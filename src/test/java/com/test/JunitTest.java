package com.test;

import com.good.good.study.singleton.Singleton;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;

import static org.junit.Assert.assertEquals;

/**
 * @author tangquanbin
 * @date 2018/9/13 15:01
 */
public class JunitTest {

    private CountDownLatch latch = new CountDownLatch(3);

    @Test
    public void testSingleton(){
        final String hashCode = String.valueOf(Singleton.getInstance().hashCode());
        for (int i=0;i<200;i++) {
            Thread thread = new Thread() {
                @Override
                public void run() {
                    assertEquals(hashCode,String.valueOf(Singleton.getInstance().hashCode()));
                }
            };
            thread.start();
        }

        try {
            latch.await(); // 主线程等待
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
