### 一、类继承关系
ArrayDeque和LinkedList一样都实现了双端队列Deque接口，但它们内部的数据结构和使用方法却不一样。根据该类的源码注释翻译可知：

- ArrayDeque实现了Deque是一个动态数组。
- ArrayDeque没有容量限制，容量会在使用时按需扩展。
- ArrayDeque不是线程安全的，前面一篇文章介绍Queue时提到的Java原生实现的 [Stack](https://www.cnblogs.com/monkjavaer/p/10964283.html)是线程安全的，所以它的性能比Stack好。
- 禁止空元素。
- ArrayDeque当作为栈使用时比Stack快，当作为队列使用时比LinkedList快。
```
public class ArrayDeque<E> extends AbstractCollection<E>
                           implements Deque<E>, Cloneable, Serializable
```

所以ArrayDeque既可以作为队列（包括双端队列xxxFirst,xxxLast），也可以作为栈（pop/push/peek）使用，而且它的效率也是非常高，下面就让我们一起来读一读jdk1.8的源码。

### 二、类属性

```
    //存储队列元素的数组
    //power of two
    transient Object[] elements; 
    
    //队列头部元素的索引
    transient int head;

    //添加一个元素的索引
    transient int tail;
    
    //最小的初始化容量（指定大小构造器使用）
    private static final int MIN_INITIAL_CAPACITY = 8;
```
- elements是transient修饰，所以elements不能被序列化，这个和ArrayList一样。elements数组的容量总是2的幂。
- MIN_INITIAL_CAPACITY是调用指定大小构造器时使用的最小的初始化容量，这个容量是8，为2的幂。

### 三、构造函数

```
    //默认16个长度
    public ArrayDeque() {
        elements = new Object[16];
    }

    public ArrayDeque(int numElements) {
        allocateElements(numElements);
    }

    public ArrayDeque(Collection<? extends E> c) {
        allocateElements(c.size());
        addAll(c);
    }
```
- ArrayDeque() 无参构造函数默认新建16个长度的数组。
- 上面第二个指定容量的构造函数，以及第三个通过Collection的构造函数都是用了allocateElements()方法

### 四、ArrayDeque分配空数组
ArrayDeque通过allocateElements()方法进行扩容。下面是allocateElements()源码：
```
    private void allocateElements(int numElements) {
        int initialCapacity = MIN_INITIAL_CAPACITY;
        // Find the best power of two to hold elements.
        // Tests "<=" because arrays aren't kept full.
        if (numElements >= initialCapacity) {
            initialCapacity = numElements;
            initialCapacity |= (initialCapacity >>>  1);
            initialCapacity |= (initialCapacity >>>  2);
            initialCapacity |= (initialCapacity >>>  4);
            initialCapacity |= (initialCapacity >>>  8);
            initialCapacity |= (initialCapacity >>> 16);
            initialCapacity++;

            if (initialCapacity < 0)   // Too many elements, must back off
                initialCapacity >>>= 1;// Good luck allocating 2 ^ 30 elements
        }
        elements = new Object[initialCapacity];
    }
```
- 首先将最小初始化容量8赋值给initialCapacity，通过initialCapacity和传入的大小numElements进行比较。
- 如果传入的容量小于8，那么元素数组elements的容量就是默认值8。正好是2的三次方。
- 如果传入容量大于等于8，那么就或通过右移（>>>）和二进制按位或运算（|）以此使得elements内部数组的容量为2的幂。
- 下面通过一个实例来了解大于等于8时，这段算法内部的运行：
 
```
ArrayDeque<Integer> arrayDeque = new ArrayDeque<>(8);
```
- 我们通过new一个8个容量的ArrayDeque，进入if判断使得initialCapacity = numElements;此时initialCapacity = 8 
- 然后执行 initialCapacity |= (initialCapacity >>>  1); 首先括号内的initialCapacity >>>  1 右移1位得到4，此时运算式便是initialCapacity|=4，通过二进制按位或运算，例：a |= b ，相当于a=a | b 。得到initialCapacity=12
- initialCapacity |= (initialCapacity >>>  2);同理为12和12右移两位结果的按位或运算，得到initialCapacity=15
- initialCapacity |= (initialCapacity >>>  4); 后面的步骤initialCapacity右移4位，8位，16位都是0，initialCapacity和0的按位或运算还是自己。最终得到所有位都变成了1，所以通过 initialCapacity++;得到二进制数10000。容量为2的4次方。

##### 为什么容量必须是2的幂呢？
下面就从主要函数中来找找答案。

### 五、如何扩容？
扩容是调用doubleCapacity() 方法，当head和tail值相等时，会进行扩容，扩容大小翻倍。
```
    private void doubleCapacity() {
        assert head == tail;
        int p = head;
        int n = elements.length;
        int r = n - p; // number of elements to the right of p
        int newCapacity = n << 1;
        if (newCapacity < 0)
            throw new IllegalStateException("Sorry, deque too big");
        Object[] a = new Object[newCapacity];
        System.arraycopy(elements, p, a, 0, r);
        System.arraycopy(elements, 0, a, r, p);
        elements = a;
        head = 0;
        tail = n;
    }
```
- int r = n - p; 计算出下面需要复制的长度
- int newCapacity = n << 1; 将原来的elements长度左移1位（乘2）
- 通过System.arraycopy(elements, p, a, 0, r); 先将head右边的元素拷贝到新数组a开头处。
- System.arraycopy(elements, 0, a, r, p);再将head左边的元素拷贝到a后面
- 最终 elements = a;设置head和tail

### 六、主要函数
#### add()/addLast(e)
通过位与计算找到下一个元素的位置。
```
    public boolean add(E e) {
        addLast(e);
        return true;
    }
```

```
    public void addLast(E e) {
        if (e == null)
            throw new NullPointerException();
        elements[tail] = e;
        if ( (tail = (tail + 1) & (elements.length - 1)) == head)
            doubleCapacity();
    }
```
add()函数实际上调用了addLast()函数，顾名思义这是将元素添加到队列尾。前提是不能添加空元素。
- elements[tail] = e; 首先将元素添加到tail位置，第一次tail和head都为0.
- tail = (tail + 1) & (elements.length - 1) 给tail赋值，这里先将tail指向下一个位置，也就是加一。再和elements.length - 1做**位与计算**。由于elements.length始终是2的幂，所以elements.length - 1的二进制始终是111...111(每一位二进制都是1)，当(tail + 1)比(elements.length - 1)大1时得到tail为0
-  (tail = (tail + 1) & (elements.length - 1)) == head 判断tail和head相等，通过doubleCapacity()进行扩容。
例如：初始化7个容量的队列，默认容量为8，当容量达到8时。

```
 8 & 7 = 0 (1000 & 111)
```
##### 为什么elements.length的实际长度必须是2的幂呢？
这就是为了上面说的位与计算elements.length - 1 以此得到下一个元素的位置tail。

#### addFirst()
和addLast相反，添加的元素都在队列最前面
```
    public void addFirst(E e) {
        if (e == null)
            throw new NullPointerException();
        elements[head = (head - 1) & (elements.length - 1)] = e;
        if (head == tail)
            doubleCapacity();
    }
```
- 判空
- head = (head - 1) & (elements.length - 1) 通过**位与计算**，计算head的值。head最开始为0,所以计算式为：

```
 -1 & （lements.length - 1）= lements.length - 1
```
所以第一次添加一个元素后head就变为lements.length - 1
- 最终head == tail = 0 达到扩容的条件。 

例如：

```
        ArrayDeque<Integer> arrayDeque = new ArrayDeque<>(7);
        arrayDeque.addFirst(1);
        arrayDeque.addFirst(2);
        arrayDeque.addFirst(3);
```
执行时，ArrayDeque内部数组结构变化为：

 0  |  1 |  2 |  3 | 4  | 5  | 6  | 7
--- |--- |--- |--- |--- |--- |--- |---
    |    |    | |  |    |  3 | 2  | 1

第一次添加前head为0，添加时计算：head = -1 & 7 ， 计算head得到7。

#### remove()/removeFirst()/pollFirst() 删除第一个元素
```
    public E remove() {
        return removeFirst();
    }
    
    public E removeFirst() {
        E x = pollFirst();
        if (x == null)
            throw new NoSuchElementException();
        return x;
    }

```

```
    public E pollFirst() {
        int h = head;
        @SuppressWarnings("unchecked")
        E result = (E) elements[h];
        // Element is null if deque empty
        if (result == null)
            return null;
        elements[h] = null;     // Must null out slot
        head = (h + 1) & (elements.length - 1);
        return result;
    }
```
删除元素实际上是调用pollFirst()函数。
- E result = (E) elements[h]; 获取第一个元素
- elements[h] = null; 将第一个元素置为null
- head = (h + 1) & (elements.length - 1); 位与计算head移动到下一个位置
#### size() 查看长度 

```
    public int size() {
        return (tail - head) & (elements.length - 1);
    }
```


### 七、ArrayDeque应用场景以及总结
- 正如jdk源码中说的“ArrayDeque当作为栈使用时比Stack快，当作为队列使用时比LinkedList快。” 所以，当我们需要使用栈这种数据结构时，优先选择ArrayDeque，不要选择Stack。如果作为队列操作首位两端我们应该优先选用ArrayDeque。如果需要根据索引进行操作那我们就选择LinkedList.
- ArrayDeque是一个双端队列，也是一个栈。
- 内部数据结构是一个动态的循环数组，head为头指针，tail为尾指针
- 内部elements数组的长度总是2的幂（目的是为了支持位与计算，以此得到下一个元素的位置）
- 由于tail始终指向下一个将被添加元素的位置，所以容量大小至少比已插入元素多一个长度。
- 内部是一个动态的循环数组，长度是动态扩展的，所以会有额外的内存分配，以及数组复制开销。