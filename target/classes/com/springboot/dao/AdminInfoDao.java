package com.springboot.dao;

import com.alibaba.fastjson.JSONObject;
import com.springboot.dao.daoInterface.JoinBeans;
import com.springboot.dao.daoInterface.UpdateRedisChainCode;
import com.springboot.entity.AdminInfo;
import com.springboot.entity.KeyValue;
import com.springboot.utils.fabricSDK.FabricSDK;
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
 * 超级管理员数据层
 */
@Repository
public class AdminInfoDao implements JoinBeans, UpdateRedisChainCode {

    @Autowired
    private KeyValueDao keyValueDaoImpl;
    @Resource
    private RedisUtils redisUtils;

    /**
     * 根据adminIdId获取AdminInfo表
     */
    public Map<String, Object> selectAdminInfoByAdminId(String adminId) {
        Map<String, Object> resultMap;//map就是一个键值对
        AdminInfo adminInfo = new AdminInfo();
        try {
            resultMap = MapUntils.stringToMap(redisUtils.get("adminInfo:" + adminId));
            try {
                MapUntils.mapToBean(resultMap, adminInfo);//map集合转实体类对象
            } catch (Exception e) {
                e.printStackTrace();
            }
            // Redis中有记录直接返回即可
            Slf4j.logger.info("Redis中有保存该档案的记录" + adminInfo);
        } catch (NullPointerException e) {
            Slf4j.logger.info("Redis中没有保存管理员ID为" + adminId);
            // redis没有这个记录 那么获取记录然后保存到redis
            // 添加向区块链请求的信息
            String[] initArgsQuery = {adminId};
            // 创建fabricSDK对象
            FabricSDK fabricSDK = new FabricSDK("adminInfo");
            try {
                // 开始查询
                Collection result = fabricSDK.queryChaincode(initArgsQuery);
                // 利用Iterator遍历Collection对象获取密码
                Iterator<String> it = result.iterator();
                // 应该是只返回一个  这个while用于返回多个的情况下
                while (it.hasNext()) {
                    String next = it.next();
                    //转JSONObject对象
                    JSONObject jsonObj = JSONObject.parseObject(next);
                    //取JSONObject对象中的数据放在对象中
                    adminInfo.setAdminId(adminId);
                    adminInfo.setAdminLevel(jsonObj.get("adminLevel").toString());
                    adminInfo.setAdminAddress(jsonObj.get("adminAddress").toString());
                    adminInfo.setAdminName(jsonObj.get("adminName").toString());
                    adminInfo.setAdminPassword(jsonObj.get("adminPassword").toString());
                    adminInfo.setAdminPhone(jsonObj.get("adminPhone").toString());
                    adminInfo.setAdminSex(jsonObj.get("adminSex").toString());
                }
                System.out.println(adminInfo.toString());
                // 同步合约对象更新到redis
                this.updateRedisChainCode(adminInfo);
            } catch (NullPointerException ex) {
                return null;
            }
        }
        // 无论从区块链获取还是redis获取到的都是RecordInfo对象
        return this.joinBeans(adminInfo);
    }

    /**
     * 注册AdminInfo
     */
    public boolean insertAdminInfo(AdminInfo adminInfo) {
        Slf4j.logger.info("Dao:管理员注册或更新" + adminInfo.toString());
        FabricSDK fabricSDK = new FabricSDK("adminInfo");
        String[] initArgsInvoke =
                {adminInfo.getAdminId(),
                        "{\"adminId\":\"" + adminInfo.getAdminId() + "\"," +
                                "\"adminPassword\":\"" + adminInfo.getAdminPassword() + "\"," +
                                "\"adminName\":\"" + adminInfo.getAdminName() + "\"," +
                                "\"adminSex\":\"" + adminInfo.getAdminSex() + "\"," +
                                "\"adminAddress\":\"" + adminInfo.getAdminAddress() + "\"," +
                                "\"adminPhone\":\"" + adminInfo.getAdminPhone() + "\"," +
                                "\"adminLevel\":\"" + adminInfo.getAdminLevel() + "\"}"
                };
        /**
         * 将管理员ID和管理员名字存在KeyIndex中
         * "admin"{
         * key：adminId
         * valueA：管理员名字
         * }
         * */
        KeyValue keyValue = new KeyValue("adminInfo", adminInfo.getAdminId(), adminInfo.getAdminName());
        keyValueDaoImpl.insertKeyIndex(keyValue);
        // 更新redis对该对象存储的最新记录  保证和区块链网络同步
        new Thread(() -> {//线程
            //毫秒
            try {
                Thread.sleep(REDIS_DELAY_SYN_TIME_AFTER_DATA_INSERTION_MS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            updateRedisChainCode(adminInfo);
        }).start();
        return fabricSDK.invoke(initArgsInvoke);
    }

    @Override
    public Map<String, Object> joinBeans(Object object) {
        return MapUntils.beanToMap(object);
    }

    @Override
    public boolean updateRedisChainCode(Object object) {
        AdminInfo adminInfo = (AdminInfo) object;
        // 添加记录到redis
        redisUtils.set("adminInfo:" + adminInfo.getAdminId(), adminInfo.toString());
        return true;
    }
}