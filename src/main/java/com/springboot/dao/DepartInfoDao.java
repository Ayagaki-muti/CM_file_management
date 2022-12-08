package com.springboot.dao;

import com.alibaba.fastjson.JSONObject;
import com.springboot.dao.daoInterface.JoinBeans;
import com.springboot.dao.daoInterface.UpdateRedisChainCode;
import com.springboot.entity.DepartInfo;
import com.springboot.entity.KeyValue;
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
 * 部门信息数据层
 *
 */
@Repository
public class DepartInfoDao implements JoinBeans, UpdateRedisChainCode {

    @Autowired
    private KeyValueDao keyValueDaoImpl;
    @Resource
    private RedisUtils redisUtils;

    /**
     * 根据departId获取DepartInfo表
     */
    public Map<String, Object> selectDepartInfoByDepartId(String departId) {
        Map<String, Object> resultMap;
        DepartInfo departInfo = new DepartInfo();
        try {
            resultMap = MapUntils.stringToMap(redisUtils.get("departInfo:" + departId));
            try {
                MapUntils.mapToBean(resultMap, departInfo);
            } catch (Exception e) {
                e.printStackTrace();
            }
            // Redis中有记录直接返回即可
            Slf4j.logger.info("Redis中有保存该部门的记录" + departInfo);
        } catch (NullPointerException e) {
            Slf4j.logger.info("获取部门 " + departId + "的所有信息，返回对象");
            // redis没有这个记录 那么获取记录然后保存到redis
            // 添加向区块链请求的信息
            String[] initArgsQuery = {departId};
            // 创建fabricSDK对象
            CMSDK cmSDK = new CMSDK("departInfo");
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
                    departInfo.setDepartId(departId);
                    departInfo.setDepartName(jsonObj.get("departName").toString());
                    departInfo.setDepartSuperior(jsonObj.get("departSuperior").toString());
                }
                // 同步合约对象更新到redis
                this.updateRedisChainCode(departInfo);
            } catch (NullPointerException ex) {
                return null;
            }
        }
        // 无论从区块链获取还是redis获取到的都是RecordInfo对象
        return this.joinBeans(departInfo);
    }

    /**
     * 添加DepartInfo
     */
    public boolean insertDepartInfo(DepartInfo departInfo) {
        Slf4j.logger.info("Dao:添加或者更新部门信息" + departInfo.toString());
        CMSDK cmSDK = new CMSDK("departInfo");
        String[] initArgsInvoke =
                {departInfo.getDepartId(),
                        "{\"departId\":\"" + departInfo.getDepartId() + "\"," +
                                "\"departName\":\"" + departInfo.getDepartName() + "\"," +
                                "\"departSuperior\":\"" + departInfo.getDepartSuperior() + "\"}"
                };
        /**
         * 将部门ID和KeyIndex中
         * "departInfo"{
         * key：departInfo
         * valueA：departId
         * }
         * */
        KeyValue keyValue = new KeyValue("departInfo", departInfo.getDepartId(), null);
        keyValueDaoImpl.insertKeyIndex(keyValue);
        // 更新redis对该对象存储的最新记录  保证和区块链网络同步
        new Thread(() -> {
            //毫秒
            try {
                Thread.sleep(REDIS_DELAY_SYN_TIME_AFTER_DATA_INSERTION_MS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            updateRedisChainCode(departInfo);
        }).start();
        return cmSDK.invoke(initArgsInvoke);
    }

    @Override
    public Map<String, Object> joinBeans(Object object) {
        return MapUntils.beanToMap(object);
    }

    @Override
    public boolean updateRedisChainCode(Object object) {
        DepartInfo departInfo = (DepartInfo) object;
        // 添加记录到redis
        redisUtils.set("departInfo:" + departInfo.getDepartId(), departInfo.toString());
        return true;
    }
}