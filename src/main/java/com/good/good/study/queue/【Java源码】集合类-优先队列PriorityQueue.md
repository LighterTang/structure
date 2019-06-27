### 一、类继承关系

```
public class PriorityQueue<E> extends AbstractQueue<E>
    implements java.io.Serializable {
```
PriorityQueue只实现了AbstractQueue抽象类也就是实现了Queue接口。

### 二、类属性


```
    //默认初始化容量
    private static final int DEFAULT_INITIAL_CAPACITY = 11;
    
    //通过完全二叉树（complete binary tree）实现的小顶堆
    transient Object[] queue; 

    private int size = 0;
    //比较器
    private final Comparator<? super E> comparator;
    //队列结构被改变的次数
    transient int modCount = 0; 
```
根据transient Object[] queue; 的英文注释：
> Priority queue represented as a balanced binary heap: the two children of queue[n] are queue[2*n+1] and queue[2*(n+1)].

我们可以知道PriorityQueue内部是通过完全二叉树（complete binary tree）实现的小顶堆（注意：这里我们定义的比较器为越小优先级越高）实现的。


### 三、数据结构
优先队列PriorityQueue内部是通过堆实现的。堆分为大顶堆和小顶堆：
![](https://github.com/monkjavaer/leetcode-structure/blob/master/src/main/pic/heap.jpg?raw=true)
- 大顶堆：每个结点的值都大于或等于其左右孩子结点的值，称为大顶堆；
- 小顶堆：每个结点的值都小于或等于其左右孩子结点的值，称为小顶堆。
- 堆通过数组来实现。
- PriorityQueue内部是一颗完全二叉树实现的小顶堆。父子节点下表有如下关系：

```
leftNo = parentNo*2+1

rightNo = parentNo*2+2

parentNo = (nodeNo-1)/2
```
通过上面的公式可以很轻松的根据某个节点计算出父节点和左右孩子节点。
### 四、常用方法
#### add()/offer()
其实add()方法内部也是调用了offer().下面是offer()的源码：

```
    public boolean offer(E e) {
        //不允许空
        if (e == null)
            throw new NullPointerException();
        //modCount记录队列的结构变化次数。
        modCount++;
        int i = size;
        //判断
        if (i >= queue.length)
            //扩容
            grow(i + 1);
        //size加1
        size = i + 1;
        if (i == 0)
            queue[0] = e;
        else
            //不是第一次添加，调整树结构
            siftUp(i, e);
        return true;
    }
```
我们可以知道：
- add()和offer()是不允许空值的插入。
- 和List一样，有fail-fast机制，会有modCount来记录队列的结构变化，在迭代和删除的时候校验，不通过会报ConcurrentModificationException。
- 判断当前元素size大于等于queue数组的长度，进行扩容。如果queue的大小小于64扩容为原来的两倍再加2，反之扩容为原来的1.5倍。
扩容函数源码如下：

```
    private void grow(int minCapacity) {
        int oldCapacity = queue.length;
        // Double size if small; else grow by 50%
        //如果queue的大小小于64扩容为原来的两倍再加2，反之扩容为原来的1.5倍
        int newCapacity = oldCapacity + ((oldCapacity < 64) ?
                                         (oldCapacity + 2) :
                                         //右移一位
                                         (oldCapacity >> 1));
        // overflow-conscious code
        if (newCapacity - MAX_ARRAY_SIZE > 0)
            newCapacity = hugeCapacity(minCapacity);
        queue = Arrays.copyOf(queue, newCapacity);
    }
```
- 加入第一个元素时，queue[0] = e;以后加入元素内部数据结构会进行二叉树调整，维持最小堆的特性：调用siftUp(i, e)：

```
    private void siftUp(int k, E x) {
        //比较器非空情况
        if (comparator != null)
            siftUpUsingComparator(k, x);
        else
            siftUpComparable(k, x);
    }
    //比较器非空情况
    @SuppressWarnings("unchecked")
    private void siftUpUsingComparator(int k, E x) {
        while (k > 0) {
            //利用堆的特性：parentNo = (nodeNo-1)/2
            int parent = (k - 1) >>> 1;
            Object e = queue[parent];
            if (comparator.compare(x, (E) e) >= 0)
                break;
            queue[k] = e;
            k = parent;
        }
        queue[k] = x;
    }
```
上面的源码中可知：
1. 利用堆的特点：parentNo = (nodeNo-1)/2 计算出父节点的下标，由此得到父节点：queue[parent];
2. 如果插入的元素x大于父节点e那么循环退出，不做结构调整，x就插入在队列尾：queue[k] = x;
3. 否则 queue[k] = e; k = parent; 父节点和加入的位置元素互换，如此循环，以维持最小堆。

下面是画的是向一个优先队列中添加（offer）一个元素过程的草图，以便理解：
![](https://github.com/monkjavaer/leetcode-structure/blob/master/src/main/pic/heapchange%20.jpg?raw=true)

#### poll(),获取并删除队列第一个元素

```
    public E poll() {
        if (size == 0)
            return null;
        //
        int s = --size;
        modCount++;
        //获取队头元素
        E result = (E) queue[0];
        E x = (E) queue[s];
        //将最后一个元素置空
        queue[s] = null;
        if (s != 0)
            //如果不止一个元素，调整结构
            siftDown(0, x);
        //返回队头元素
        return result;
    }
```
删除元素，也得调整结构以维持优先队列内部数据结构为：堆

### 五、简单用法

下面是一段简单的事列代码，演示了自然排序和自定义排序的情况：
```
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
                return o1.getAge()-o2.getAge();
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

```

### 六、应用场景
优先队列PriorityQueue常用于调度系统优先处理VIP用户的请求。多线程环境下我们需要使用PriorityBlockingQueue来处理此种问题，以及需要考虑队列数据持久化。