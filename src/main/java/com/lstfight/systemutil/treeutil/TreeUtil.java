package com.lstfight.systemutil.treeutil;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>树形控件工具</p>
 *
 * @author 李尚庭
 * @date 2018/8/21 0021 14:28
 */
public class TreeUtil {
    private TreeUtil() {
    }

    /**
     * 将通过parent_id组织的树形关系转换为{@link CommonTree<T>}树形数据结构
     * 注意需要有parentId字段用已判断
     *
     * @param <T>    真实树类型
     * @param source 原树形关系
     * @return 树形数据结构
     */
    public static <T extends Tree> List<CommonTree<Tree>> treeTify(List<T> source) {
        if (source != null && source.size() > 0) {
            //定义统一需要实现接口
            List<CommonTree<Tree>> commonTreeList = new ArrayList<>(8);
            //找到所有的根节点
            for (Tree s : source) {
                if (s.getParentId().equals(CommonTree.ROOT_ID)) {
                    CommonTree<Tree> commonTree = CommonTree.build().addParent(s);
                    commonTreeList.add(commonTree);
                    //为根节点添加子节点 可以使用递归进行
                    fullChild(commonTree, (List<Tree>) source);
                }
            }
            return commonTreeList;
        }
        return null;
    }

    /**
     * 为父节点填充孩子节点
     *
     * @param tree   树形结构实体
     * @param source 树形关系表
     */
    private static void fullChild(CommonTree tree, List<Tree> source) {
        List<CommonTree> childList = new ArrayList<>();
        for (Tree s : source) {
            if (tree.getNode().getNodeId().equals(s.getParentId())) {
                CommonTree<Tree> currentTree = CommonTree.build().addParent(s);
                childList.add(currentTree);
                //为当前节点添加子节点 实现多级嵌套添加
                fullChild(currentTree, source);
            }
        }
        if (childList.size() == 0) {
            childList = null;
        }
        tree.addChildList(childList);
    }


    /**
     * 为转换为树形结构时指定孩子节点名称
     * 需要依赖Jackson注解实现，且有延迟，暂不推荐使用
     */
    public static <T extends Tree> List<CommonTree<Tree>> treeTify(List<T> source, String childListName) {
        try {
            Field childList =CommonTree.class.getDeclaredField("childList");
            Annotation jsonAlias = childList.getAnnotation(JsonProperty.class);
            AnnotationUtil.changeAnnotationFileValue(jsonAlias, "value", childListName);
        } catch (ReflectiveOperationException e) {
            e.printStackTrace();
        }
        return treeTify(source);
    }
}
