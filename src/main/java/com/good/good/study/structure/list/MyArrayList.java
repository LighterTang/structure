package com.good.good.study.structure.list;

/**
 * @Title: MyArrayList
 * @Package: com.good.good.study.structure.list
 * @Description: 实现线性表顺序结构
 * @Author: tangquanbin
 * @Data: 2019/4/11 0011 21:00
 * @Version: V1.0
 */
public class MyArrayList implements BaseList {

    /**
     * 线性表长度
     */
    private int size;

    /**
     * 线性表可能最大数据元素
     */
    private static final int MAX_SIZE = 10;

    public  Object[] data = new Object[MAX_SIZE];


    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        for (int i = 0; i < size; i++) {
            data[i] = null;
        }
        size = 0;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * 插入算法
     * 最好情况，在最后插入时间复杂度O(1);
     * 最坏情况，在第一个位置插入时间复杂度为O(n);
     *
     * @param index  插入元素的位置
     * @param object
     */
    @Override
    public void add(int index, Object object) {
        if (size == MAX_SIZE) {
            System.out.println("线性表满了。。。");
            return;
        }

        if (index < 0 || index > size) {
            System.out.println("插入位置不合法。。。");
            return;
        }else {
            for (int i = size - 1; i > index - 1; i--) {
                data[i + 1] = data[i];
            }
            data[index] = object;
            size++;
        }
    }

    @Override
    public Object get(int index) {
        return data[index];
    }

    @Override
    public Object remove(int index) {
        Object object;
        if (index < 0 || index > size) {
            System.out.println("删除位置不合法。。。");
            return null;
        }
        object = data[index];
        for (int i = index; i < size; i++) {
            data[i] = data[i + 1];
        }
        size--;
        return object;
    }

    @Override
    public Object getPre(Object object) {
        return null;
    }

    @Override
    public Object getNext(Object object) {
        return null;
    }

}
