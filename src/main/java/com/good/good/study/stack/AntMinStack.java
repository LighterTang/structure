package com.good.good.study.stack;

import java.util.Stack;

/**
 * 1：最小函数min()栈
 *
 * 1. 设计含最小函数min()、取出元素函数pop()、放入元素函数push()的栈AntMinStack，实现其中指定的方法
 * 2. AntMinStack中数据存储使用Java原生的Stack，存储数据元素为int。请实现下面对应的方法，完善功能。
 * @author tangquanbin
 * @date 2018/09/11 21:44
 */
public class AntMinStack {
    /**
     * 真正存放数据栈
     */
    public static Stack<Integer> stack = new Stack<>();

    /**
     * 存放最小数栈
     */
    public static Stack<Integer> minStack = new Stack<>();

    /**
     * push 放入元素
     * @param data
     */
    public void push(int data) {
        stack.push(data);
        if (minStack.size()==0||data<minStack.peek()){
            minStack.push(data);
        }else {
            minStack.push(minStack.peek());
        }
    }

    /**
     * pop 推出元素
     * @return
     * @throws Exception
     */
    public int pop() throws Exception {
        minStack.pop();
        return stack.pop();
    }

    /**
     * min 最小函数，调用该函数，可直接返回当前AntMinStack的栈的最小值
     *
     * @return
     * @throws Exception
     */
    public int min() throws Exception {
        return minStack.peek();
    }

    public static void main(String[] args){
        AntMinStack antMinStack = new AntMinStack();
        antMinStack.push(2);
        antMinStack.push(1);
        antMinStack.push(8);
        antMinStack.push(9);
        antMinStack.push(1);
        try {
            System.out.println("最小值："+antMinStack.min());
            antMinStack.pop();
            antMinStack.pop();
            System.out.println("最小值："+antMinStack.min());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
