package com.springboot.dao;

import com.alibaba.fastjson.JSONObject;
import com.springboot.dao.daoInterface.JoinBeans;
import com.springboot.dao.daoInterface.UpdateRedisChainCode;
import com.springboot.entity.KeyValue;
import com.springboot.entity.UserInfo;
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
 * 普通用户数据层
 *
 */
@Repository
public class UserInfoDao implements JoinBeans, UpdateRedisChainCode {

    @Autowired
    private KeyValueDao keyValueDaoImpl;
    @Resource
    private RedisUtils redisUtils;

    /**
     * 根据userId获取UserInfo表
     */
    public Map<String, Object> selectUserInfoByUserId(String userId) {
        Map<String, Object> resultMap;
        UserInfo userInfo = new UserInfo();
        try {
            resultMap = MapUntils.stringToMap(redisUtils.get("userInfo:" + userId));
            try {
                MapUntils.mapToBean(resultMap, userInfo);
            } catch (Exception e) {
                e.printStackTrace();
            }
            // Redis中有记录直接返回即可
            Slf4j.logger.info("Redis中有保存该用户的记录" + userId);
        } catch (NullPointerException e) {
            Slf4j.logger.info("Redis中有保存该用户 ID为" + userId);
            // redis没有这个记录 那么获取记录然后保存到redis
            // 添加向区块链请求的信息
            String[] initArgsQuery = {userId};
            // 创建fabricSDK对象
            CMSDK cmSDK = new CMSDK("userInfo");
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
                    userInfo.setUserId(userId);
                    userInfo.setUserAddress(jsonObj.get("userAddress").toString());
                    userInfo.setUserName(jsonObj.get("userName").toString());
                    userInfo.setUserPassword(jsonObj.get("userPassword").toString());
                    userInfo.setUserPhone(jsonObj.get("userPhone").toString());
                    userInfo.setUserSex(jsonObj.get("userSex").toString());
                    // 同步合约对象更新到redis
                    this.updateRedisChainCode(userInfo);
                }
            } catch (NullPointerException ex) {
                return null;
            }
        }
        // 无论从区块链获取还是redis获取到的都是RecordInfo对象
        return this.joinBeans(userInfo);
    }

    /**
     * 注册UserInfo
     */
    public boolean insertUserInfo(UserInfo userInfo) {
        Slf4j.logger.info("Dao:用户注册或更新" + userInfo.toString());
        CMSDK cmSDK = new CMSDK("userInfo");
        String[] initArgsInvoke =
                {userInfo.getUserId(),
                        "{\"userId\":\"" + userInfo.getUserId() + "\"," +
                                "\"userPassword\":\"" + userInfo.getUserPassword() + "\"," +
                                "\"userName\":\"" + userInfo.getUserName() + "\"," +
                                "\"userSex\":\"" + userInfo.getUserSex() + "\"," +
                                "\"userAddress\":\"" + userInfo.getUserAddress() + "\"," +
                                "\"userPhone\":\"" + userInfo.getUserPhone() + "\"}"
                };
        /**
         * 将用户信息ID记录到 KeyIndex中
         * "userInfo"{
         * key：userInfo
         * valueA：userInfoId
         * }
         * */
        KeyValue keyValue = new KeyValue("userInfo", userInfo.getUserId(), null);
        keyValueDaoImpl.insertKeyIndex(keyValue);
        // 更新redis对该对象存储的最新记录  保证和区块链网络同步
        new Thread(() -> {
            //毫秒
            try {
                Thread.sleep(REDIS_DELAY_SYN_TIME_AFTER_DATA_INSERTION_MS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            updateRedisChainCode(userInfo);
        }).start();
        return cmSDK.invoke(initArgsInvoke);
    }

    @Override
    public Map<String, Object> joinBeans(Object object) {
        return MapUntils.beanToMap(object);
    }

    @Override
    public boolean updateRedisChainCode(Object object) {
        UserInfo userInfo = (UserInfo) object;
        // 添加记录到redis
        redisUtils.set("userInfo:" + userInfo.getUserId(), userInfo.toString());
        return true;
    }
}