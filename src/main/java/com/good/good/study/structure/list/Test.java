package com.good.good.study.structure.list;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @Title: Test
 * @Package: com.good.good.study.structure.list
 * @Description: TODO（添加描述）
 * @Author: monkjavaer
 * @Data: 2019/4/11 0011 22:03
 * @Version: V1.0
 */
public class Test {

    public static void main(String[] args) {
        MyArrayList list = new MyArrayList();
        list.add(0,0);
        list.add(1,1);
        list.add(2,2);
        list.add(3,3);
        list.add(4,4);

        System.out.println("size"+list.size()+Arrays.toString(list.data));

        list.add(1,5);
        System.out.println("size"+list.size()+Arrays.toString(list.data));
        list.remove(3);
        System.out.println("size"+list.size()+Arrays.toString(list.data));


    }
}
