package com.good.good.study.structure.list;

/**
 * @Title: MyLinkedList
 * @Package: com.good.good.study.structure.list
 * @Description: TODO（添加描述）
 * @Author: tangquanbin
 * @Data: 2019/4/11 0011 23:52
 * @Version: V1.0
 */
public class MyLinkedList implements BaseList{
    private int data;
    private MyArrayList next;
    private int size;

    public MyLinkedList(int data) {
        this.data = data;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {

    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public void add(int index, Object object) {



    }

    @Override
    public Object get(int index) {
        return null;
    }

    @Override
    public Object remove(int index) {
        return null;
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
