package com.good.good.study.expression;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * 计算工具
 * @author tangquanbin
 * @date 2018/9/13 14:35
 */
public class CalculatorUtil {
    /**
     * 加
     */
    private static final String ADD = "+";
    /**
     * 减
     */
    private static final String SUBTRACT = "-";
    /**
     * 乘
     */
    private static final String MULTIPLICATION = "*";
    /**
     * 除
     */
    private static final String DIVISION = "/";
    /**
     * 左括号
     */
    private static final String LEFT_PARENTHESIS = "(";
    /**
     * 右括号
     */
    private static final String RIGHT_PARENTHESIS = ")";


    /**
     *
     * 提供给外部的计算方法
     *
     * @param expression 后缀表达式
     * @return 计算结果
     */
    public static int calculation(Expression expression) throws Exception {
        List<String> list = getPostfix(expression);
        Stack<Integer> calculationStack = new Stack<>();
        Integer operandRight;
        Integer operandLeft;
        for (String item : list) {
            if (ADD.equals(item)) {
                operandRight  = calculationStack.pop();
                operandLeft = calculationStack.pop();
                calculationStack.push(operandLeft + operandRight);
            } else if (SUBTRACT.equals(item)) {
                operandRight  = calculationStack.pop();
                operandLeft = calculationStack.pop();
                calculationStack.push(operandLeft - operandRight);
            } else if (MULTIPLICATION.equals(item)) {
                operandRight  = calculationStack.pop();
                operandLeft = calculationStack.pop();
                calculationStack.push(operandLeft * operandRight);
            } else if (DIVISION.equals(item)) {
                operandRight  = calculationStack.pop();
                operandLeft = calculationStack.pop();
                calculationStack.push(operandLeft / operandRight);
            } else {
                calculationStack.push(Integer.parseInt(item));
            }
        }

        return calculationStack.pop();
    }

    /**
     * 判断字符为运算符(+,-,*,/)
     *
     * @param c 输入字符
     * @return
     */
    private static boolean isOperator(char c) {

        return ADD.equals(String.valueOf(c)) || SUBTRACT.equals(String.valueOf(c)) ||
                MULTIPLICATION.equals(String.valueOf(c)) || DIVISION.equals(String.valueOf(c));
    }

    /**
     * 返回的是运算符的优先级
     *
     * @param operator
     * @return
     */
    private static int priority(Operator operator) {
        char operatorName = operator.getOperatorName();
        if (ADD.equals(String.valueOf(operatorName)) || SUBTRACT.equals(String.valueOf(operatorName))) {
            return 1;
        } else if (MULTIPLICATION.equals(String.valueOf(operatorName)) || DIVISION.equals(String.valueOf(operatorName))) {
            return 2;
        } else {
            return 0;
        }
    }

    /**
     * 通过表达式获得后缀表达式
     *
     * @param expression
     * @return
     */
    private static List<String> getPostfix(Expression expression) throws Exception {
        /**
         * 操作符栈
         */
        Stack<Operator> operatorStack = new Stack<>();

        /**
         * 存放后缀表达式
         */
        List<String> operandList = new ArrayList<>();

        String expressionStr = expression.getExpressionStr();
        for (int i = 0; i < expressionStr.length(); i++) {
            char oneChar = expressionStr.charAt(i);

            Operator operator = new Operator();
            operator.setOperatorName(oneChar);

            //遇到操作数：直接输出（添加到后缀表达式中）
            if (Character.isDigit(oneChar)) {
                int num = oneChar - '0';
                while (i + 1 < expressionStr.length() && Character.isDigit(expressionStr.charAt(i + 1))) {
                    num = num * 10 + expressionStr.charAt(i + 1) - '0';
                    i++;
                }
                operandList.add(String.valueOf(num));
            } else if (LEFT_PARENTHESIS.equals(String.valueOf(oneChar))) {
                //遇到左括号：将其入栈
                operatorStack.push(operator);
            } else if (RIGHT_PARENTHESIS.equals(String.valueOf(oneChar))) {
                //遇到右括号：执行出栈操作，并将出栈的元素输出，直到弹出栈的是左括号，左括号不输出。
                while (!LEFT_PARENTHESIS.equals(String.valueOf(operatorStack.peek().getOperatorName()))) {
                    operandList.add(String.valueOf(operatorStack.pop().getOperatorName()));
                }
                //然后弹出左括号
                operatorStack.pop();
            } else if (isOperator(oneChar)) {
                //遇到运算符
                //栈为空时，直接入栈
                if (operatorStack.isEmpty()) {
                    operatorStack.push(operator);
                } else {
                    // 如果读入的操作符为非")"且优先级比栈顶元素的优先级高或一样
                    if (priority(operatorStack.peek()) < priority(operator)) {
                        operatorStack.push(operator);
                    } else if (priority(operatorStack.peek()) >= priority(operator)) {
                        operandList.add(String.valueOf(operatorStack.pop().getOperatorName()));
                        operatorStack.push(operator);
                    }
                }
            }
        }

        //最终将栈中的元素依次出栈。
        while (!operatorStack.isEmpty()) {
            operandList.add(String.valueOf(operatorStack.pop().getOperatorName()));
        }
        System.out.println(operandList);
        return operandList;
    }

}
