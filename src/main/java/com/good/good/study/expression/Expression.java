package com.good.good.study.expression;

import java.util.Stack;

/**
 * 表达式
 *
 * @author tangquanbin
 * @date 2018/9/12 19:42
 */
public class Expression {

    private Stack<Operator> stack = new Stack<>();
    public int getResult() {

        //存放运算符



        return 0;
    }

    /**
     * 通过表达式获得后缀表达式
     * @param expression
     * @return
     */
    public String getPostfix(String expression) throws Exception{
        StringBuilder stringBuilder = new StringBuilder();
        for (int i=0;i<expression.length();i++){
            Operator operator = new Operator();
            operator.setOperatorName(expression.charAt(i));
            char c = expression.charAt(i);
            if (Character.isDigit(c)){
                //遇到操作数：直接输出（添加到后缀表达式中）
                stringBuilder.append(c);
            }
            if (isOperator(c)){
                //栈为空时，遇到运算符，直接入栈
                if (stack.isEmpty()){
                    stack.push(operator);
                }else {
                    // 若栈顶元素优先级大于或等于要入栈的元素
                    if (priority(stack.peek()) >= priority(operator)){
                        stringBuilder.append(stack.pop());
                    }
                }
            }
            if (isOpenParent(c)){

            }
            if (isCloseParent(c)){

            }
        }
        return stringBuilder.toString();
    }



    /**
     * 判断字符为左括号
     * @param c
     * @return
     */
    public boolean isOpenParent(char c){
        return c=='(';
    }

    /**
     * 判断字符为右括号
     * @param c
     * @return
     */
    public boolean isCloseParent(char c){
        return c==')';
    }

    /**
     * 判断字符为运算符
     * @param c
     * @return
     */
    public boolean isOperator(char c){
        if('+'==c||'-'==c||'*'==c||'/'==c){
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * 返回的是运算符的优先级
     *
     * @param operator
     * @return
     */
    public static int priority(Operator operator) {
        char operatorName = operator.getOperatorName();
        if (operatorName == '+' || operatorName == '-') {
            return 1;
        } else if (operatorName == '*' || operatorName == '/') {
            return 2;
        } else {
            return 0;
        }
    }


}
