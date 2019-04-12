package com.good.good.study.structure.list;

/**
 * @Title: SingleLinkedList
 * @Package: com.good.good.study.structure.list
 * @Description: 单向链表
 * @Author: tangquanbin
 * @Data: 2019/4/12 0012 21:33
 * @Version: V1.0
 */
public class SingleLinkedList {

    /**
     * 头指针
     */
    private Node head;

    /**
     * 链表大小
     */
    private int size;

    /**
     * 构造空的单向链表
     */
    public SingleLinkedList() {
        this.head = null;
    }

    /**
     * 构造指向next的单向链表
     * @param next
     */
    public SingleLinkedList(Node next) {
        this.head = next;
    }

    public int size() {
        return size;
    }

    /**
     * 是否为空
     * @return true 空
     */
    public boolean isEmpty() {
        return head == null;
    }

    /**
     * 在node节点后添加，数据域为data的节点
     * @param data
     * @param node
     */
    public void add(int data, Node node) {
        Node p = new Node();
        p.data = data;
        p.next = node.next;
        node.next = p;
        size++;
    }

    /**
     * 删除node的后继节点
     * @param node
     * @return
     */
    public void remove(Node node) {
        Node temp;
        if (node.next!=null){
            //找到后继结点
            temp = node.next;
            //删除节点
            node.next = temp.next;
            //释放后继节点空间
            temp = null;
            size--;
        }
    }

    /**
     * 获取单链表第index个节点
     * @param index
     * @return
     */
    public Node get(int index) {
        Node p = head.next;
        int i = 1;
        while (p != null && i<index){
            p = p.next;
            i++;
        }
        if (i == index){
            return p;
        }
        return null;
    }


    public class Node {
        /**
         * 数据域
         */
        private int data;
        /**
         * 指针域（链域）
         */
        private Node next;

        public Node(int data) {
            this.data = data;
            next = null;
        }

        public Node() {
            this(0);
        }
    }

}
