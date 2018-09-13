package com.good.good.study.expression;

/**
 * @author tangquanbin
 * @date 2018/9/13 11:49
 */
public class Test {


    public static void main(String[] args) {
        Expression expression = new Expression();
        expression.setExpressionStr("4 +(13 - 5)");
        try {
            System.out.println(CalculatorUtil.calculation(expression));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
