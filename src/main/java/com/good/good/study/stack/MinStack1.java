package com.good.good.study.stack;

import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

/**
 * @author tangquanbin
 * @date 2018/9/12 16:15
 */
public class MinStack1 {
    Stack<Integer> stack;
    List<Integer> list;

    public MinStack1() {
        stack = new Stack<>();
        list = new LinkedList<>();
    }

    public void push(int x) {
        stack.push(x);
        if (list.isEmpty() || x <= list.get(list.size() - 1)) {
            list.add(x);
        }
    }

    public void pop() {
        int cur = stack.pop();
        if (cur == list.get(list.size() - 1)) {
            list.remove(list.size() - 1);
        }
    }

    public int top() {
        return stack.peek();
    }

    public int getMin() {
        return list.get(list.size() - 1);
    }

    public static void main(String[] args) {
        MinStack1 minStack1 = new MinStack1();
        minStack1.push(5);
        minStack1.push(3);
        minStack1.push(7);
        minStack1.push(1);
        System.out.println(minStack1.getMin());
    }

}
