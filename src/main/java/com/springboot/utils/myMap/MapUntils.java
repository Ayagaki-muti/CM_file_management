package com.springboot.utils.myMap;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.springboot.utils.myLog.Slf4j;
import org.springframework.cglib.beans.BeanMap;

import java.io.IOException;
import java.util.*;

public class MapUntils {

    /**
     * 将对象装换为map
     *
     * @param bean
     * @return
     */
    public static <T> Map<String, Object> beanToMap(T bean) {
        Map<String, Object> map = Maps.newHashMap();
        if (bean != null) {
            BeanMap beanMap = BeanMap.create(bean);
            for (Object key : beanMap.keySet()) {
                map.put(key + "", beanMap.get(key));
            }
        }
        return map;
    }

    /**
     * 将map装换为javabean对象
     *
     * @param map
     * @param bean
     * @return
     */
    public static <T> T mapToBean(Map<String, Object> map, T bean) {
        BeanMap beanMap = BeanMap.create(bean);
        beanMap.putAll(map);
        return bean;
    }


    /**
     * 将List<T>转换为List<Map<String, Object>>
     *
     * @param objList
     * @return
     * @throws JsonGenerationException
     * @throws JsonMappingException
     * @throws IOException
     */
    public static <T> List<Map<String, Object>> objectsToMaps(List<T> objList) {
        List<Map<String, Object>> list = Lists.newArrayList();
        if (objList != null && objList.size() > 0) {
            Map<String, Object> map;
            T bean;
            for (int i = 0, size = objList.size(); i < size; i++) {
                bean = objList.get(i);
                map = beanToMap(bean);
                list.add(map);
            }
        }
        return list;
    }

    /**
     * 将List<Map<String,Object>>转换为List<T>
     *
     * @param maps
     * @param clazz
     * @return
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public static <T> List<T> mapsToObjects(List<Map<String, Object>> maps, Class<T> clazz) throws InstantiationException, IllegalAccessException {
        List<T> list = Lists.newArrayList();
        if (maps != null && maps.size() > 0) {
            Map<String, Object> map = null;
            T bean = null;
            for (int i = 0, size = maps.size(); i < size; i++) {
                map = maps.get(i);
                bean = clazz.newInstance();
                mapToBean(map, bean);
                list.add(bean);
            }
        }
        return list;
    }

    /**
     * 输出Map<String,Object>
     **/
    public static String printListMap(Map<String, Object> map) {
        return map.toString();
    }

    /**
     * Map.toString()的字符串转为Map对象
     **/
    public static Map<String, Object> stringToMap(String str) {
        str = str.substring(1, str.length() - 1);
        String[] strs = str.split(",");
        Map<String, Object> map = new HashMap<>();
        for (String string : strs) {
            String key = string.split("=")[0];
            Object value = string.split("=")[1];
            map.put(key.trim(), value);
        }
        return map;
    }

    /**
     * 输出List<Map<String,Object>>
     **/
    public static List<Map<String, Object>> stringToListMap(String str) {
        List<Map<String, Object>> result = new ArrayList<>();
        str = str.trim().replace(" ", "");
        System.out.println("str:" + str);
        if (str.length() >= 4) {
            String[] strsMap = str.substring(2, str.length() - 2).split("\\},\\{");
            for (String mapStr : strsMap
            ) {
                String[] strMap = mapStr.split(",");
                Map<String, Object> map = new HashMap<>();
                for (String string : strMap) {
                    String key = string.split("=")[0];
                    Object value = string.split("=")[1];
                    map.put(key.trim(), value);
                }
                result.add(map);
            }
        }
        return result;
    }

    /**
     * 输出List<Map<String,Object>>
     **/
    public static String printListMap(List<Map<String, Object>> map) {
        String result = "[";
        try {
            //取list集合里的那一条Map集合
            int i = 0;
            for (Map<String, Object> m : map) {
                result += m.toString();
                i++;
                if (i != map.size()) {
                    result += ",";
                }
            }
        } catch (NullPointerException e) {
            Slf4j.logger.info("集合为空");
        } finally {
            result += "]";
            return result;
        }
    }

    /**
     * 排序List<Map<String,Object>>
     *
     * @param maps    待排序List Map
     * @param sortKey 排序的key
     */
    public static void sort(List<Map<String, Object>> maps, String sortKey) {
        Collections.sort(maps, new Comparator<Map<String, Object>>() {
            @Override
            public int compare(Map<String, Object> o1, Map<String, Object> o2) {
                Object name1 = o1.get(sortKey);//name1是从你list里面拿出来的一个
                Object name2 = o2.get(sortKey); //name1是从你list里面拿出来的第二个name
                if (name1 instanceof String) {
                    return (String.valueOf(name1)).compareTo(String.valueOf(name2));
                } else if (name1 instanceof Integer) {
                    return Integer.valueOf(name1.toString()) - Integer.valueOf(name2.toString());
                } else {
                    return 1;
                }
            }
        });
    }

    /**
     * 排序List<Map<String,Object>>
     *
     * @param maps         待排序List Map
     * @param sortKey      排序的key
     * @param ascendeOrder 升序为true 降序为false
     */
    public static void sort(List<Map<String, Object>> maps, String sortKey, Boolean ascendeOrder) {
        Collections.sort(maps, new Comparator<Map<String, Object>>() {
            @Override
            public int compare(Map<String, Object> o1, Map<String, Object> o2) {
                Object name1 = o1.get(sortKey);//name1是从你list里面拿出来的一个
                Object name2 = o2.get(sortKey); //name1是从你list里面拿出来的第二个name
                if (!ascendeOrder) {
                    Object temp = name1;
                    name1 = name2;
                    name2 = temp;
                }
                if (name1 instanceof String) {
                    return ((String) name1).compareTo((String) name2);
                } else if (name1 instanceof Integer) {
                    return (Integer) name1 - (Integer) name2;
                } else {
                    return 1;
                }
            }
        });
    }

}