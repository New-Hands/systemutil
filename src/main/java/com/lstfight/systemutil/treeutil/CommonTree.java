package com.lstfight.systemutil.treeutil;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;

import java.util.List;
import java.util.Objects;

/**
 * 定义树形数据结构
 * <p>支持流式风格API</p>
 *
 * @author 李尚庭
 * @date 2018/8/21 0021 14:14
 */
public class CommonTree<T extends Tree> {
    static final String ROOT_ID = "0";

    private static final String childAlias = "children";
    /**
     * 当前节点
     * 在序列化时不添加根节点
     */
    @JsonUnwrapped
    private T node;

    /**
     * 孩子节点列表
     * 使用JsonProperty指定属性名
     */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonProperty(childAlias)
    private List<CommonTree> childList;

    /**
     * 解决泛型
     *
     * @return 树结构
     */
    public static <W extends Tree> CommonTree<W> build() {
        return new CommonTree<>();
    }

    /**
     * 流式编程支持
     *
     * @param node 树当前节点信息
     * @return 当前树
     */
    CommonTree<T> addParent(T node) {
        this.setNode(node);
        return this;
    }

    /**
     * 流式编程支持
     *
     * @param childList 树孩子列表
     * @return 当前树
     */
    CommonTree<T> addChildList(List<CommonTree> childList) {
        this.setChildList(childList);
        return this;
    }

    public T getNode() {
        return node;
    }

    public void setNode(T node) {
        this.node = node;
    }

    public List<CommonTree> getChildList() {
        return childList;
    }

    public void setChildList(List<CommonTree> childList) {
        this.childList = childList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CommonTree<?> that = (CommonTree<?>) o;
        return Objects.equals(node, that.node) &&
                Objects.equals(childList, that.childList);
    }

    @Override
    public int hashCode() {

        return Objects.hash(node, childList);
    }
}
