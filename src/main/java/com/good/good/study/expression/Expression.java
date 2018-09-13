package com.good.good.study.expression;

/**
 * 表达式
 *
 * @author tangquanbin
 * @date 2018/9/12 19:42
 */
public class Expression {

    /**
     * 表达式名字
     */
    private String name;

    /**
     * 表达式
     */
    private String expressionStr;

    /**
     * 表达式值
     */
    private int value;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExpressionStr() {
        return expressionStr;
    }

    public void setExpressionStr(String expressionStr) {
        this.expressionStr = expressionStr;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

}
