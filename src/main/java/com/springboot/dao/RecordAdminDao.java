package com.springboot.dao;

import com.alibaba.fastjson.JSONObject;
import com.springboot.dao.daoInterface.JoinBeans;
import com.springboot.dao.daoInterface.UpdateRedisChainCode;
import com.springboot.entity.KeyValue;
import com.springboot.entity.RecordAdmin;
import com.springboot.utils.chainmakerSDK.CMSDK;
import com.springboot.utils.myLog.Slf4j;
import com.springboot.utils.myMap.MapUntils;
import com.springboot.utils.myRedis.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import static com.springboot.config.StaticConfig.REDIS_DELAY_SYN_TIME_AFTER_DATA_INSERTION_MS;

/**
 * 档案管理员数据层
 */
@Repository
public class RecordAdminDao implements JoinBeans, UpdateRedisChainCode {

    @Autowired
    private KeyValueDao keyValueDaoImpl;
    @Autowired
    private DepartInfoDao departInfoDaoImpl;
    @Resource
    private RedisUtils redisUtils;

    /**
     * 根据recordAdminId获取RecordAdmin表
     */
    public Map<String, Object> selectRecordAdminByRecordAdminId(String recordAdminId) {
        Map<String, Object> resultMap;
        RecordAdmin recordAdmin = new RecordAdmin();
        try {
            resultMap = MapUntils.stringToMap(redisUtils.get("recordAdmin:" + recordAdminId));
            try {
                MapUntils.mapToBean(resultMap, recordAdmin);
            } catch (Exception e) {
                e.printStackTrace();
            }
            // Redis中有记录直接返回即可
            Slf4j.logger.info("Redis中有保存该档案管理员的记录" + recordAdmin);
        } catch (NullPointerException e) {
            Slf4j.logger.info("Redis中没有保存该档案管理员的记录" + recordAdminId);
            // redis没有这个记录 那么获取记录然后保存到redis
            // 添加向区块链请求的信息
            String[] initArgsQuery = {recordAdminId};
            // 创建fabricSDK对象
            CMSDK cmSDK = new CMSDK();
            try {
                // 开始查询
                Collection result = cmSDK.queryChaincode("recordAdmin",initArgsQuery);
                // 利用Iterator遍历Collection对象获取密码
                Iterator<String> it = result.iterator();
                // 应该是只返回一个  这个while用于返回多个的情况下
                while (it.hasNext()) {
                    String next = it.next();
                    //转JSONObject对象
                    JSONObject jsonObj = JSONObject.parseObject(next);
                    //取JSONObject对象中的数据放在对象中
                    recordAdmin.setDepartId(jsonObj.get("departId").toString());
                    recordAdmin.setRecordAdminId(recordAdminId);
                    recordAdmin.setRecordAdminLevel(jsonObj.get("recordAdminLevel").toString());
                    recordAdmin.setRecordAdminName(jsonObj.get("recordAdminName").toString());
                    recordAdmin.setRecordAdminPassword(jsonObj.get("recordAdminPassword").toString());
                    recordAdmin.setRecordAdminPhone(jsonObj.get("recordAdminPhone").toString());
                }
                // 同步合约对象更新到redis
                this.updateRedisChainCode(recordAdmin);
            } catch (NullPointerException ex) {
                return null;
            }
        }
        // 无论从区块链获取还是redis获取到的都是RecordInfo对象
        return this.joinBeans(recordAdmin);
    }

    /**
     * 注册RecordAdmin
     */
    public boolean insertRecordAdmin(RecordAdmin recordAdmin) {
        Slf4j.logger.info("Dao:添加RecordInfo" + recordAdmin.toString());
        CMSDK cmSDK = new CMSDK();
        String[] initArgsInvoke =
                {recordAdmin.getRecordAdminId(),
                        "{\"recordAdminId\":\"" + recordAdmin.getRecordAdminId() + "\"," +
                                "\"recordAdminPassword\":\"" + recordAdmin.getRecordAdminPassword() + "\"," +
                                "\"recordAdminName\":\"" + recordAdmin.getRecordAdminName() + "\"," +
                                "\"departId\":\"" + recordAdmin.getDepartId() + "\"," +
                                "\"recordAdminLevel\":\"" + recordAdmin.getRecordAdminLevel() + "\"," +
                                "\"recordAdminPhone\":\"" + recordAdmin.getRecordAdminPhone() + "\"}"
                };
        /**
         * 将部门ID和KeyIndex中
         * "recordAdmin"{
         * key：recordAdmin
         * valueA：recordAdminId
         * }
         * */
        KeyValue keyValue = new KeyValue("recordAdmin", recordAdmin.getRecordAdminId(), null);
        keyValueDaoImpl.insertKeyIndex(keyValue);
        // 更新redis对该对象存储的最新记录  保证和区块链网络同步
        new Thread(() -> {
            //毫秒
            try {
                Thread.sleep(REDIS_DELAY_SYN_TIME_AFTER_DATA_INSERTION_MS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            updateRedisChainCode(recordAdmin);
        }).start();
        return cmSDK.invoke("recordAdmin",initArgsInvoke);
    }

    @Override
    public Map<String, Object> joinBeans(Object object) {
        RecordAdmin recordAdmin = (RecordAdmin) object;
        Map<String, Object> resultMap = MapUntils.beanToMap(recordAdmin);
        if (!recordAdmin.getDepartId().equals("null")) {
            try {
                resultMap.putAll(departInfoDaoImpl.selectDepartInfoByDepartId(recordAdmin.getDepartId()));
            } catch (NullPointerException e) {
                Slf4j.logger.info("部门信息不存在");
            }
        }
        return resultMap;
    }

    @Override
    public boolean updateRedisChainCode(Object object) {
        RecordAdmin recordAdmin = (RecordAdmin) object;
        // 添加记录到redis
        redisUtils.set("recordAdmin:" + recordAdmin.getRecordAdminId(), recordAdmin.toString());
        return true;
    }
}