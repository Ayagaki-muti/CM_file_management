package com.springboot.dao;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.springboot.dao.daoInterface.InsertToRedisHistory;
import com.springboot.dao.daoInterface.JoinBeans;
import com.springboot.entity.KeyValue;
import com.springboot.utils.chainmakerSDK.CMSDK;
import com.springboot.utils.myLog.Slf4j;
import com.springboot.utils.myMap.MapUntils;
import com.springboot.utils.myRedis.RedisUtils;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.*;

import static com.springboot.config.StaticConfig.REDIS_DELAY_SYN_TIME_AFTER_DATA_INSERTION_MS;


@Repository
public class KeyValueDao implements JoinBeans, InsertToRedisHistory {

    @Resource
    private RedisUtils redisUtils;

    /**
     * 根据key（id）获取所有KeyIndex表
     */
    public List<Map<String, Object>> selectAllKeyIndexByKey(String key) {
        try {
            String redisResult = redisUtils.get("keyValueHistory:" + key);
            if(redisResult == null){
                throw new NullPointerException();
            }
            Slf4j.logger.info("Redis中有保存该keyValue的历史记录" + key);
            HashSet<Map<String, Object>> resultSet = new HashSet<>(MapUntils.stringToListMap(redisResult));
            return new ArrayList<>(resultSet);
        } catch (NullPointerException e) {
            // redis没有这个记录 那么获取历史记录然后保存到redis
            Slf4j.logger.info("Redis中没有保存该档案的历史记录" + key);
            Set<Map<String, Object>> resultSet = new HashSet<>();
            // 添加向区块链请求的信息
            String[] initArgsQuery = {key};
            // 创建fabricSDK对象
            CMSDK cmSDK = new CMSDK();
            try {
                // 开始查询
                Collection collection = cmSDK.queryAllChaincode("keyValue",initArgsQuery);
                // 利用Iterator遍历Collection对象获取密码
                Iterator<String> it = collection.iterator();
                // 应该是只返回一个  这个while用于返回多个的情况下
                //{key:value,key:value,.....}
                while (it.hasNext()) {
                    String next = it.next();
                    //转JSONObject对象
                    JSONObject jsonObj = JSONObject.parseObject(next);
                    JSONArray re = (JSONArray) jsonObj.get("recordInfos");
                    for (int i = 0; i < re.size(); i++) {
                        //取JSONObject对象中的数据放在对象中
                        KeyValue keyValue = new KeyValue();
                        keyValue.setKey(re.getJSONObject(i).get("key").toString());
                        keyValue.setValueA(re.getJSONObject(i).get("valueA").toString());
                        keyValue.setValueB(re.getJSONObject(i).get("valueB").toString());
                        Map<String, Object> map = this.joinBeans(keyValue);
                        resultSet.add(map);
                    }
                }
                Slf4j.logger.info("Dao::" + resultSet);
                // 添加记录到redis
                redisUtils.set("keyValueHistory:" + key, resultSet.toString());
                return new ArrayList<>(resultSet);
            } catch (NullPointerException ex) {
                ex.printStackTrace();
                return null;
            }
        }
    }

    /**
     * 添加KeyIndex
     */
    public boolean insertKeyIndex(KeyValue keyValue) {
        Slf4j.logger.info("Dao:添加KeyIndex信息" + keyValue.toString());
        CMSDK cmSDK = new CMSDK();
        String[] initArgsInvoke =
                {keyValue.getKey(),
                        "{\"Key\":\"" + keyValue.getKey() + "\"," +
                                "\"ValueA\":\"" + keyValue.getValueA() + "\"," +
                                "\"ValueB\":\"" + keyValue.getValueB() + "\"}"
                };
        // 更新redis对该对象存储的历史记录  保证和区块链网络同步
        new Thread(() -> {
            try {
                //毫秒
                Thread.sleep(REDIS_DELAY_SYN_TIME_AFTER_DATA_INSERTION_MS);
                insertToRedisHistory(keyValue);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        return cmSDK.invoke("keyValue",initArgsInvoke);
    }

    /**
     * 返回 合约的下一个ID
     */
    public Integer getNextChainCodeId(String chainCode) throws NullPointerException {
        List<Map<String, Object>> temp = this.selectAllKeyIndexByKey(chainCode);
        Integer max = 0;
        for (Map<String, Object> k : temp
        ) {
            System.out.println(k.toString());
            KeyValue keyValue = new KeyValue();
            MapUntils.mapToBean(k, keyValue);
            if (!keyValue.getValueA().equals("null")) {
                Integer nextId = Integer.valueOf(keyValue.getValueA());
                if (nextId > max) {
                    max = nextId;
                }
            }
        }
        return ++max;//找最大id的下一个id
    }

    /**
     * 如果已经给这位用户授权过这个档案那么返回档案的授权ID 否则返回null
     */
    public String selectRecordAuthByUserId2RecordId(String userId, String recordId) {
        //List<Map<String,Object>> temp = this.selectAllKeyIndexByKey(userId);
        /*for (Map<String,Object> k:temp
        ) {
            KeyValue keyValue = new KeyValue();
            MapUntils.mapToBean(k,keyValue);
            // 如果keyvalue中记录过将该档案授权给该用户那么不允许重复授权
            if(keyValue.getValueB().equals(recordId)){
                return keyValue.getValueA();
            }
        }*/
        return null;
    }

    @Override
    public Map<String, Object> joinBeans(Object object) {
        return MapUntils.beanToMap(object);
    }

    @Override
    public boolean insertToRedisHistory(Object object) {
        KeyValue keyValue = (KeyValue) object;
        List<Map<String, Object>> preMap;
        try {
            // redis中有这个记录 需要添加这条数据
            preMap = MapUntils.stringToListMap(redisUtils.get("keyValueHistory:" + keyValue.getKey()));
            preMap.add(this.joinBeans(keyValue));
            System.out.println("preMap::" + preMap.toString());
        } catch (NullPointerException e) {
            // redis没有这个记录 那么获取历史记录然后保存到redis
            Slf4j.logger.info("Redis中没有保存该keyValue的历史记录 ID：" + keyValue.getKey());
            preMap = this.selectAllKeyIndexByKey(keyValue.getKey());
        }
        // 添加记录到redis
        System.out.println("Set:" + keyValue.getKey() + ":" + preMap.toString());
        redisUtils.set("keyValueHistory:" + keyValue.getKey(), preMap.toString());
        return true;
    }

}