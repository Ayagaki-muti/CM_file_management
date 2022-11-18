package com.springboot.dao.daoInterface;

/**
 * <h3>FileManage</h3>
 * <p>更新redis链码实现和区块链网络同步接口 </p>

 **/
public interface UpdateRedisChainCode {
    /**
     * 此接口用于实现Redis和区块链网络同步链码对象
     *
     * @param object:合约对象 具体对象需要在接口实现中转换
     * @return false:同步更新失败
     */
    boolean updateRedisChainCode(Object object);

}
