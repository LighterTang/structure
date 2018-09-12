package com.good.good.study.expression;

/**
 * 运算符
 * @author tangquanbin
 * @date 2018/9/12 19:43
 */
public class Operator {

    /**
     * 运算符符号
     */
    private char name;

    public char getName() {
        return name;
    }

    public void setName(char name) {
        this.name = name;
    }

    /**
     * 判断符号优先级的方法
     * @param c
     * @return
     */
    public static int optionLevel(char c) {
        switch (c) {
            case '+':
            case '-':
                return 1;
            case '*':
            case '/':
                return 2;
            default:
                return 0;
        }
    }
}
