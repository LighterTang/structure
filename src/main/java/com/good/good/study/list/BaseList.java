package com.good.good.study.list;

/**
 * @Title: BaseList
 * @Package: com.good.good.study.structure.list
 * @Description: TODO（添加描述）
 * @Author: monkjavaer
 * @Data: 2019/4/11 0011 20:46
 * @Version: V1.0
 */
public interface BaseList {

    /**
     * 线性表大小
     * @return list大小
     */
    int size();

    /**
     * 清空
     */
    void clear();

    /**
     * 是否为空
     * @return true 为空
     */
    boolean isEmpty();

    /**
     * 在index前插入元素
     * @param index 插入元素的位置
     * @param object
     */
    void add(int index,Object object);

    /**
     * 获取元素
     * @param index 元素的位置
     * @return 返回
     */
    Object get(int index);

    /**
     * 删除元素
     * @param index 元素的位置
     * @return
     */
    Object remove(int index);

    /**
     * 获取前驱元素
     * @param object
     * @return
     */
    Object getPre(Object object);

    /**
     * 获取后继元素
     * @param object
     * @return
     */
    Object getNext(Object object);

}
