package com.springboot.dao;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.springboot.dao.daoInterface.InsertToRedisHistory;
import com.springboot.dao.daoInterface.JoinBeans;
import com.springboot.dao.daoInterface.UpdateRedisChainCode;
import com.springboot.entity.KeyValue;
import com.springboot.entity.RecordInfo;
import com.springboot.utils.chainmakerSDK.CMSDK;
import com.springboot.utils.myLog.Slf4j;
import com.springboot.utils.myMap.MapUntils;
import com.springboot.utils.myRedis.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.*;

import static com.springboot.config.StaticConfig.REDIS_DELAY_SYN_TIME_AFTER_DATA_INSERTION_MS;

/**
 * 档案信息数据层
 *
 */
@Repository
public class RecordInfoDao implements JoinBeans, InsertToRedisHistory, UpdateRedisChainCode {

    @Autowired
    private KeyValueDao keyValueDaoImpl;
    @Autowired
    private RecordAdminDao recordAdminDaoImpl;
    @Resource
    private RedisUtils redisUtils;

    /**
     * 根据adminIdId获取最新AdminInfo表
     */
    public Map<String, Object> selectRecordInfoByRecordId(String recordId) {
        Map<String, Object> resultMap;
        RecordInfo recordInfo = new RecordInfo();
        try {
            resultMap = MapUntils.stringToMap(redisUtils.get("recordInfo:" + recordId));
            try {
                MapUntils.mapToBean(resultMap, recordInfo);
            } catch (Exception e) {
                e.printStackTrace();
            }
            // Redis中有记录直接返回即可
            Slf4j.logger.info("Redis中有保存该档案的记录" + recordId);
        } catch (NullPointerException e) {
            Slf4j.logger.info("Redis中没有保存该档案的记录" + recordId);
            try {
                // redis没有这个记录 那么获取记录然后保存到redis
                // 添加向区块链请求的信息
                String[] initArgsQuery = {recordId};
                // 创建fabricSDK对象
                CMSDK cmSDK = new CMSDK();
                // 开始查询
                Collection result = cmSDK.queryChaincode("recordInfo",initArgsQuery);
                // 利用Iterator遍历Collection对象获取密码
                Iterator<String> it = result.iterator();
                // 应该是只返回一个  这个while用于返回多个的情况下
                while (it.hasNext()) {
                    String next = it.next();
                    //转JSONObject对象
                    JSONObject jsonObj = JSONObject.parseObject(next);
                    //取JSONObject对象中的数据放在对象中
                    recordInfo.setFileHash(jsonObj.get("fileHash").toString());
                    recordInfo.setRecordAdminId(jsonObj.get("recordAdminId").toString());
                    recordInfo.setRecordDescribe(jsonObj.get("recordDescribe").toString());
                    recordInfo.setRecordId(recordId);
                    recordInfo.setRecordName(jsonObj.get("recordName").toString());
                    recordInfo.setRecordTime(jsonObj.get("recordTime").toString());
                    recordInfo.setRecordVersion(jsonObj.get("recordVersion").toString());
                }
                // 同步合约对象更新到redis
                this.updateRedisChainCode(recordInfo);
            } catch (NullPointerException ex) {
                return null;
            }
        }
        // 无论从区块链获取还是redis获取到的都是RecordInfo对象
        return this.joinBeans(recordInfo);
    }

    /**
     * 根据recordId获取所有RecordInfo表
     */
    public List<Map<String, Object>> selectAllRecordInfoByRecordId(String recordId) {
        try {
            String redisResult = redisUtils.get("recordInfoHistory:" + recordId);
            Slf4j.logger.info("Redis中有保存该档案的历史记录" + recordId);
            return MapUntils.stringToListMap(redisResult);
        } catch (NullPointerException e) {
            // redis没有这个记录 那么获取历史记录然后保存到redis
            Slf4j.logger.info("Redis中没有保存该档案的历史记录" + recordId);
            List<Map<String, Object>> result = new ArrayList<>();
            // 添加向区块链请求的信息
            String[] initArgsQuery = {recordId};
            // 创建fabricSDK对象
            CMSDK cmSDK = new CMSDK();
            try {
                // 开始查询
                Collection collection = cmSDK.queryAllChaincode("recordInfo",initArgsQuery);
                // 利用Iterator遍历Collection对象获取密码
                Iterator<String> it = collection.iterator();
                // 应该是只返回一个  这个while用于返回多个的情况下
                while (it.hasNext()) {
                    String next = it.next();
                    //转JSONObject对象
                    JSONObject jsonObj = JSONObject.parseObject(next);
                    JSONArray re = (JSONArray) jsonObj.get("recordInfos");
                    for (int i = 0; i < re.size(); i++) {
                        //取JSONObject对象中的数据放在对象中
                        RecordInfo recordInfo = new RecordInfo();
                        recordInfo.setFileHash(re.getJSONObject(i).get("fileHash").toString());
                        recordInfo.setRecordAdminId(re.getJSONObject(i).get("recordAdminId").toString());
                        recordInfo.setRecordDescribe(re.getJSONObject(i).get("recordDescribe").toString());
                        recordInfo.setRecordId(re.getJSONObject(i).get("recordId").toString());
                        recordInfo.setRecordName(re.getJSONObject(i).get("recordName").toString());
                        recordInfo.setRecordTime(re.getJSONObject(i).get("recordTime").toString());
                        recordInfo.setRecordVersion(re.getJSONObject(i).get("recordVersion").toString());
                        Map<String, Object> map = this.joinBeans(recordInfo);
                        result.add(map);
                    }
                }
                Slf4j.logger.info("Dao::" + result);
                // 添加记录到redis
                redisUtils.set("recordInfoHistory:" + recordId, result.toString());
                return result;
            } catch (NullPointerException ex) {
                return null;
            }
        }
    }

    /**
     * 添加RecordInfo
     */
    public boolean insertRecordInfo(RecordInfo recordInfo) {
        Slf4j.logger.info("Dao:添加档案信息" + recordInfo.toString());
        CMSDK cmSDK = new CMSDK();
        String[] initArgsInvoke =
                {recordInfo.getRecordId(),
                        "{\"recordId\":\"" + recordInfo.getRecordId() + "\"," +
                                "\"recordAdminId\":\"" + recordInfo.getRecordAdminId() + "\"," +
                                "\"recordName\":\"" + recordInfo.getRecordName() + "\"," +
                                "\"recordVersion\":\"" + recordInfo.getRecordVersion() + "\"," +
                                "\"recordTime\":\"" + recordInfo.getRecordTime() + "\"," +
                                "\"fileHash\":\"" + recordInfo.getFileHash() + "\"," +
                                "\"recordDescribe\":\"" + recordInfo.getRecordDescribe() + "\"}"
                };
        /**
         * 将档案信息ID记录到 KeyIndex中
         * "recordInfo"{
         * key：recordInfo
         * valueA：recordInfoId
         * }
         * */
        KeyValue keyValue = new KeyValue("recordInfo", recordInfo.getRecordId(), null);
        keyValueDaoImpl.insertKeyIndex(keyValue);
        // 更新redis对该对象存储的历史记录  保证和区块链网络同步
        new Thread(() -> {
            try {
                //毫秒
                Thread.sleep(REDIS_DELAY_SYN_TIME_AFTER_DATA_INSERTION_MS);
                updateRedisChainCode(recordInfo);
                insertToRedisHistory(recordInfo);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        return cmSDK.invoke("recordInfo",initArgsInvoke);
    }


    @Override
    public boolean insertToRedisHistory(Object object) {
        RecordInfo recordInfo = (RecordInfo) object;
        List<Map<String, Object>> preMap;
        try {
            // redis中有这个记录 需要添加这条数据
            preMap = MapUntils.stringToListMap(redisUtils.get("recordInfoHistory:" + recordInfo.getRecordId()));
            preMap.add(this.joinBeans(recordInfo));
        } catch (NullPointerException e) {
            // redis没有这个记录 那么获取历史记录然后保存到redis
            Slf4j.logger.info("Redis中没有保存该档案的历史记录 ID：" + recordInfo.getRecordId());
            preMap = this.selectAllRecordInfoByRecordId(recordInfo.getRecordId());
        }
        // 添加记录到redis
        redisUtils.set("recordInfoHistory:" + recordInfo.getRecordId(), preMap.toString());
        return true;
    }

    @Override
    public Map<String, Object> joinBeans(Object object) {
        RecordInfo recordInfo = (RecordInfo) object;
        Map<String, Object> resultMap = MapUntils.beanToMap(recordInfo);
        if (!"null".equals(recordInfo.getRecordAdminId())) {
            try {
                resultMap.putAll(recordAdminDaoImpl.selectRecordAdminByRecordAdminId(String.valueOf(recordInfo.getRecordAdminId())));
            } catch (NullPointerException e) {
                Slf4j.logger.info("档案管理员不存在");
            } catch (NumberFormatException e) {
                Slf4j.logger.info("档案管理员不存在");
            }
        }
        return resultMap;
    }

    @Override
    public boolean updateRedisChainCode(Object object) {
        RecordInfo recordInfo = (RecordInfo) object;
        // 添加记录到redis
        redisUtils.set("recordInfo:" + recordInfo.getRecordId(), recordInfo.toString());
        return true;
    }
}