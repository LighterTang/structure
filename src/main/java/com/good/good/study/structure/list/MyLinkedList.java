package com.good.good.study.structure.list;

/**
 * @Title: MyLinkedList
 * @Package: com.good.good.study.structure.list
 * @Description: TODO（添加描述）
 * @Author: tangquanbin
 * @Data: 2019/4/12 8:26
 * @Version: V1.0
 */
public class MyLinkedList implements BaseList{

    /**
     * 头结点
     */
    private Node header = new Node(null,null);

    class Node{
        /**
         * 数据域
         */
        private Object data;
        /**
         * 指针域
         */
        private Node next;

        public Node(Object data, Node next) {
            this.data = data;
            this.next = next;
        }
    }

    @Override
    public int size() {
        return 0;
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
