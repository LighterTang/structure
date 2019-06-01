package com.good.good.study.stack;

import java.util.Stack;

/**
 * ###155题.设计一个支持 push，pop，top 操作，并能在常数时间内检索到最小元素的栈。
 *     push(x) -- 将元素 x 推入栈中。
 *     pop() -- 删除栈顶的元素。
 *     top() -- 获取栈顶元素。
 *     min() -- 检索栈中的最小元素。
 * @author monkjavaer
 * @date 2018/09/11 21:44
 */
public class MinStack {
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
     * 获取栈顶元素
     * @return
     */
    public int top() {
        return stack.peek();

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
        MinStack antMinStack = new MinStack();
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
