package com.good.good.study.queue;

/**
 * @author monkjavaer
 * @version V1.0
 * @date 2019/6/16 0016 10:22
 */
public class Person {

    private Integer age;
    private String name;

    Person() {
    }

    public Person(Integer age) {
        this.age = age;
    }

    public Person(Integer age, String name) {
        this.age = age;
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
