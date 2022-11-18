package com.springboot.dao.daoInterface;

/**
 * <h3>FileManage</h3>
 * <p>更新redis历史记录实现和区块链网络同步接口 </p>
 *
 **/
public interface InsertToRedisHistory {
    /**
     * 因为在向区块链网络插入数据的时候会造成历史记录添加 需要进行同步
     * 所以该接口的功能是保证区块链网络中的历史记录数据和Redis中历史记录数据保持一致
     * 如果不涉及历史记录的查询比如用户信息的合约不需要实现该接口
     *
     * @param object:合约对象 具体对象需要在接口实现中转换
     * @return false:更新失败
     */
    boolean insertToRedisHistory(Object object);

}
