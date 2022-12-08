package com.springboot.dao;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.springboot.dao.daoInterface.InsertToRedisHistory;
import com.springboot.dao.daoInterface.JoinBeans;
import com.springboot.dao.daoInterface.UpdateRedisChainCode;
import com.springboot.entity.KeyValue;
import com.springboot.entity.RecordAuth;
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
 * 档案授权信息数据层

 */
@Repository
public class RecordAuthDao implements JoinBeans, InsertToRedisHistory, UpdateRedisChainCode {

    @Autowired
    private KeyValueDao keyValueDaoImpl;
    @Autowired
    private RecordAdminDao recordAdminDaoImpl;
    @Autowired
    private UserInfoDao userInfoDaoImpl;
    @Resource
    private RedisUtils redisUtils;

    /**
     * 根据recordAuthId获取最新recordAuth表
     */
    public Map<String, Object> selectRecordAuthByRecordAuthId(String recordAuthId) {
        Map<String, Object> resultMap;
        RecordAuth recordAuth = new RecordAuth();
        try {
            resultMap = MapUntils.stringToMap(redisUtils.get("recordAuth:" + recordAuthId));
            try {
                MapUntils.mapToBean(resultMap, recordAuth);
            } catch (Exception e) {
                e.printStackTrace();
            }
            // Redis中有记录直接返回即可
            Slf4j.logger.info("Redis中有保存档案授权的记录 ID：" + recordAuthId);
        } catch (NullPointerException e) {
            Slf4j.logger.info("Redis中没有保存档案授权的记录 ID：" + recordAuthId);
            // redis没有这个记录 那么获取记录然后保存到redis
            // 添加向区块链请求的信息
            String[] initArgsQuery = {recordAuthId};
            // 创建fabricSDK对象
            CMSDK cmSDK = new CMSDK("recordAuth");
            try {
                // 开始查询
                Collection result = cmSDK.queryChaincode(initArgsQuery);
                // 利用Iterator遍历Collection对象获取密码
                Iterator<String> it = result.iterator();
                // 应该是只返回一个  这个while用于返回多个的情况下
                while (it.hasNext()) {
                    String next = it.next();
                    //转JSONObject对象
                    JSONObject jsonObj = JSONObject.parseObject(next);
                    //取JSONObject对象中的数据放在对象中
                    recordAuth.setRecordAdminId(jsonObj.get("recordAdminId").toString());
                    recordAuth.setRecordUserId(jsonObj.get("recordUserId").toString());
                    recordAuth.setRecordAuthCutOffTime(jsonObj.get("recordAuthCutOffTime").toString());
                    recordAuth.setRecordAuthId(String.valueOf(jsonObj.get("recordAuthId").toString()));
                    recordAuth.setRecordAuthTime(jsonObj.get("recordAuthTime").toString());
                    recordAuth.setRecordId(String.valueOf(jsonObj.get("recordId").toString()));
                    recordAuth.setRecordAuthTips(jsonObj.get("recordAuthTips").toString());
                }
                // 同步合约对象更新到redis
                this.updateRedisChainCode(recordAuth);
            } catch (NullPointerException ex) {
                return null;
            }
        }
        // 无论从区块链获取还是redis获取到的都是RecordInfo对象
        return this.joinBeans(recordAuth);
    }

    /**
     * 根据recordAuthId获取所有recordAuth表
     */
    public List<Map<String, Object>> selectAllRecordAuthByRecordAuthId(String recordAuthId) {
        try {
            String redisResult = redisUtils.get("recordAuthHistory:" + recordAuthId);
            Slf4j.logger.info("Redis中有保存该档案授权的历史记录" + recordAuthId);
            return MapUntils.stringToListMap(redisResult);
        } catch (NullPointerException e) {
            // redis没有这个记录 那么获取历史记录然后保存到redis
            Slf4j.logger.info("Redis中没有有保存该档案授权的历史记录" + recordAuthId);
            List<Map<String, Object>> result = new ArrayList<>();
            RecordAuth recordAuth;
            // 添加向区块链请求的信息
            String[] initArgsQuery = {recordAuthId};
            // 创建fabricSDK对象
            CMSDK cmSDK = new CMSDK("recordAuth");
            try {
                // 开始查询
                Collection collection = cmSDK.queryAllChaincode(initArgsQuery);
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
                        recordAuth = new RecordAuth();
                        recordAuth.setRecordUserId(re.getJSONObject(i).get("recordUserId").toString());
                        recordAuth.setRecordAdminId(re.getJSONObject(i).get("recordAdminId").toString());
                        recordAuth.setRecordAuthCutOffTime(re.getJSONObject(i).get("recordAuthCutOffTime").toString());
                        recordAuth.setRecordAuthId(String.valueOf(re.getJSONObject(i).get("recordAuthId").toString()));
                        recordAuth.setRecordAuthTime(re.getJSONObject(i).get("recordAuthTime").toString());
                        recordAuth.setRecordId(String.valueOf(re.getJSONObject(i).get("recordId").toString()));
                        recordAuth.setRecordAuthTips(re.getJSONObject(i).get("recordAuthTips").toString());
                        Map<String, Object> map = this.joinBeans(recordAuth);
                        result.add(map);
                    }
                }
                Slf4j.logger.info("Dao::" + result);
                // 添加记录到redis
                redisUtils.set("recordAuthHistory:" + recordAuthId, result.toString());
                return result;
            } catch (NullPointerException ex) {
                return null;
            }
        }

    }

    /**
     * 添加RecordAuth
     * key为所属人 所以在查询指定所属人所有授权信息的时候直接根据key查找就可以
     */
    public boolean insertRecordAuth(RecordAuth recordAuth) {
        Slf4j.logger.info("Dao:添加RecordAuth：" + recordAuth.toString());
        CMSDK cmSDK = new CMSDK("recordAuth");
        String[] initArgsInvoke =
                {recordAuth.getRecordAuthId(),
                        "{\"recordAuthId\":\"" + recordAuth.getRecordAuthId() + "\"," +
                                "\"recordAdminId\":\"" + recordAuth.getRecordAdminId() + "\"," +
                                "\"recordUserId\":\"" + recordAuth.getRecordUserId() + "\"," +
                                "\"recordId\":\"" + recordAuth.getRecordId() + "\"," +
                                "\"recordAuthTime\":\"" + recordAuth.getRecordAuthTime() + "\"," +
                                "\"recordAuthTips\":\"" + recordAuth.getRecordAuthTips() + "\"," +
                                "\"recordAuthCutOffTime\":\"" + recordAuth.getRecordAuthCutOffTime() + "\"}"
                };
        /**
         * 将所属人ID和授权记录ID记录存在KeyIndex中
         * 所属人ID{
         * key：所属人ID
         * valueA：档案ID
         * }
         * */
        System.out.println("Dao授权信息" + recordAuth);
        KeyValue keyValue = new KeyValue(recordAuth.getRecordUserId(), recordAuth.getRecordAuthId(), recordAuth.getRecordId());
        keyValueDaoImpl.insertKeyIndex(keyValue);
        keyValue = new KeyValue("recordAuth", recordAuth.getRecordAuthId(), recordAuth.getRecordId());
        keyValueDaoImpl.insertKeyIndex(keyValue);
        // 更新redis对该对象存储的历史记录  保证和区块链网络同步
        new Thread(() -> {
            try {
                //毫秒
                Thread.sleep(REDIS_DELAY_SYN_TIME_AFTER_DATA_INSERTION_MS);
                updateRedisChainCode(recordAuth);
                insertToRedisHistory(recordAuth);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        return cmSDK.invoke(initArgsInvoke);
    }

    @Override
    public Map<String, Object> joinBeans(Object object) {
        RecordAuth recordAuth = (RecordAuth) object;
        Map<String, Object> resultMap = MapUntils.beanToMap(recordAuth);
        if (!"null".equals(recordAuth.getRecordUserId())) {
            try {
                resultMap.putAll(userInfoDaoImpl.selectUserInfoByUserId(String.valueOf(recordAuth.getRecordUserId())));
            } catch (NullPointerException e) {
                Slf4j.logger.info("用户不存在");
            }
        }
        if (!"null".equals(recordAuth.getRecordAdminId())) {
            try {
                resultMap.putAll(recordAdminDaoImpl.selectRecordAdminByRecordAdminId(String.valueOf(recordAuth.getRecordAdminId())));
            } catch (NullPointerException e) {
                Slf4j.logger.info("档案管理员不存在");
            }
        }
        return resultMap;
    }

    @Override
    public boolean insertToRedisHistory(Object object) {
        RecordAuth recordAuth = (RecordAuth) object;
        List<Map<String, Object>> preMap;
        try {
            System.out.println(recordAuth.toString());
            preMap = MapUntils.stringToListMap(redisUtils.get("recordAuthHistory:" + recordAuth.getRecordUserId()));
            // redis中有这个记录 需要添加这条数据
            preMap.add(this.joinBeans(recordAuth));
        } catch (NullPointerException e) {
            // redis没有这个记录 那么获取历史记录然后保存到redis
            Slf4j.logger.info("Redis中没有保存该档案授权的历史记录：" + recordAuth.getRecordAuthId());
            preMap = this.selectAllRecordAuthByRecordAuthId(recordAuth.getRecordAuthId());
        }
        // 添加记录到redis
        redisUtils.set("recordAuthHistory:" + recordAuth.getRecordUserId(), preMap.toString());
        return true;
    }

    @Override
    public boolean updateRedisChainCode(Object object) {
        RecordAuth recordAuth = (RecordAuth) object;
        // 添加记录到redis
        redisUtils.set("recordAuth:" + recordAuth.getRecordAuthId(), recordAuth.toString());
        return true;
    }

}