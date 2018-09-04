package com.lstfight.systemutil.treeutil;

/**
 * <p>抽象树形关系</p>
 *
 * @author 李尚庭
 * @date 2018/8/21 0021 14:57
 */
public interface Tree {
    /**
     * 获取父亲节点ID
     *
     * @return 父亲节点ID
     */
    String getParentId();

    /**
     * 获取当前节点ID
     *
     * @return 当前节点ID
     */
    String getNodeId();
}
