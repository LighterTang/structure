package com.good.good.study.singleton;

/**
 * 3：提供一个懒汉模式的单实例类实现，并满足如下要求：
 *     1.考虑线程安全。
 *     2.基于junit提供测试代码，模拟并发，测试线程安全性，给出对应的断言。
 * @author tangquanbin
 * @date 2018/9/12 19:04
 */
public class Singleton {

    private Singleton(){

    }

    public static Singleton getInstance(){
        return  SingletonHelper.instance;
    }

    /**
     * 静态内部内
     */
    private static class SingletonHelper{
        private static final Singleton instance = new Singleton();
    }

}
