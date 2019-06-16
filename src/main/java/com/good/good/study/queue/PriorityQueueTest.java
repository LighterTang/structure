package com.good.good.study.queue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Random;

/**
 * @author monkjavaer
 * @version V1.0
 * @date 2019/6/16 0016 10:22
 */
public class PriorityQueueTest {

    /**日志记录*/
    private static final Logger logger = LoggerFactory.getLogger(PriorityQueueTest.class);

    public static void main(String[] args) {
        naturalOrdering();
        personOrdering();
    }

    /**
     * 自然排序
     */
    private static void naturalOrdering(){
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>();
        Random random = new Random();
        int size = 10;
        for (int i =0;i<size;i++){
            priorityQueue.add(random.nextInt(100));
        }
        for (int i =0;i<size;i++){
            logger.info("第 {} 次取出元素：{}",i,priorityQueue.poll());
        }
    }

    /**
     * 自定义排序规则，根据人的年龄排序
     */
    private static void personOrdering(){
        PriorityQueue<Person> priorityQueue = new PriorityQueue<>(new Comparator<Person>() {
            @Override
            public int compare(Person o1, Person o2) {
                return o2.getAge()-o1.getAge();
            }
        });
        Random random = new Random();
        int size = 10;
        for (int i =0;i<size;i++){
            priorityQueue.add(new Person(random.nextInt(100)));
        }
        while (true){
            Person person = priorityQueue.poll();
            if (person == null){
                break;
            }
            logger.info("取出Person：{}",person.getAge());
        }
    }

}
