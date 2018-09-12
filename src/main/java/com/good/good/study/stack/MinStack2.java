package com.good.good.study.stack;

import java.util.Stack;

/**
 * @author tangquanbin
 * @date 2018/9/12 16:24
 */
public class MinStack2 {
    Stack<Integer> stack = new Stack<>();
    int min = Integer.MAX_VALUE;

    public void push(int x) {
        if (x <= min) {
            stack.push(min);
            min = x;
        }
        stack.push(x);
    }

    public void pop() {
        int peek = stack.pop();
        if (peek == min){
            min = stack.pop();
        }
    }

    public int top() {
        return stack.peek();
    }

    public int size(){
        return stack.size();
    }
    public int getMin() {
        return min;
    }
    public static void main(String[] args) {
        MinStack2 minStack = new MinStack2();
        Integer integer = null;
        minStack.push(5);
        minStack.push(3);
        minStack.push(7);
        minStack.push(1);
        minStack.push(1);
        minStack.push(integer);
        System.out.println(minStack.size());
        System.out.println(minStack.getMin());
    }

}
