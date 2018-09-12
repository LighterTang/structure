package com.good.good.study.singleton;

/**
 * 静态内部类
 * @author tangquanbin
 * @date 2018/9/12 19:04
 */
public class Singleton1 {

    private static class SingletonInstance{
        private static final Singleton1 instance = new Singleton1();
    }

    private Singleton1(){

    }

    public static Singleton1 getInstance(){
        return  SingletonInstance.instance;
    }
}
