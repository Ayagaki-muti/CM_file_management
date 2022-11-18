package com.springboot.dao.daoInterface;

import java.util.Map;

public interface JoinBeans {
    /**
     * 和其他表进行连接
     */
    Map<String, Object> joinBeans(Object object);
}
