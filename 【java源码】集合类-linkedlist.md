### 一、类继承关系
LinkedList和ArrayList都实现了List接口。所以有List的特性，同时LinkedList也实现了Deque,所以它也具有双端队列和栈的特性。
```
public class LinkedList<E>
    extends AbstractSequentialList<E>
    implements List<E>, Deque<E>, Cloneable, java.io.Serializable
```

### 二、类属性

```
    //实际元素个数
    transient int size = 0;
    //头结点
    transient Node<E> first;
    //尾结点
    transient Node<E> last;
```
transient表示该域不能被序列化。first，last初始值都是null.

这里有一个内部类Node：

```
    private static class Node<E> {
        //数据
        E item;
        //后继
        Node<E> next;
        //前驱
        Node<E> prev;

        Node(Node<E> prev, E element, Node<E> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
    }
```



### 三、构造函数

```
    public LinkedList() {
    }

    public LinkedList(Collection<? extends E> c) {
        this();
        addAll(c);
    }
```
LinkedList内部的数据结构是一个双向链表，所以不会有ArrayList那样的指定容量构造器。

### 四、LinkedList如何扩容
- LinkedList内部的数据结构是一个双向链表，既没有初始化大小，也没有扩容机制一说。其大小是需要时才会分配，不需要分配多余的。

###  五、主要函数
#### add(E e) 函数

```
    public boolean add(E e) {
        linkLast(e);
        return true;
    }
```

```
    void linkLast(E e) {
        //final类型的l节点保存尾结点last
        final Node<E> l = last;
        //创建一个新的节点newNode，其前驱为l,后继为null
        final Node<E> newNode = new Node<>(l, e, null);
        //将尾结点赋值为新创建的节点。
        last = newNode;
        //尾结点为空，第一次添加
        if (l == null)
            //新建节点为头结点
            first = newNode;
        else
            //否则以前的尾结点的后继指向新节点
            l.next = newNode;
        //集合大小加1
        size++;
        //结构性变化加一，目的和ArrayList一样，检查迭代过程中结构变化。
        modCount++;
    }
```
add()方法会将新添加的元素添加到链表的尾端。
- 在linkList中，首先会将原来的尾结点last保存在一个不可变的final节点l中。
- 添加的元素会被新建为一个Node节点，其前驱指向以前的尾结点l,其后继为null。
- 然后将尾结点赋值给last，成为新的尾结点。
- 判断以前的尾结点是否为空（第一次添加），如果为空,新建节点就是头结点first。如果尾结点不是空，l.next = newNode;表示以前的尾结点的后继指向新节点。
- 然后LinkedList集合大小加1。modCount++;表示LinkedList集合结构性变化加一，目的和ArrayList一样，检查迭代过程中结构变化。

让我们通过debug看看LinkedList添加元素过程，其结构的变化。
测试代码：

```
    public static void main(String[] args) {
        LinkedList<Integer> linkedList = new LinkedList<>();
        linkedList.add(1);
        linkedList.add(2);
        linkedList.add(3);
    }
```
第一次添加后结构为：

![](https://github.com/monkjavaer/leetcode-structure/blob/master/src/main/pic/1.png?raw=true)

第二次添加后结构为：

![](https://github.com/monkjavaer/leetcode-structure/blob/master/src/main/pic/2.png?raw=true)

第三次添加后结构为：

![](https://github.com/monkjavaer/leetcode-structure/blob/master/src/main/pic/3.png?raw=true)

#### remove(int index) 函数
```
    public E remove(int index) {
        //检查是否越界
        checkElementIndex(index);
        return unlink(node(index));
    }
```
先通过node方法获取index处的节点：

```
/**
     * Returns the (non-null) Node at the specified element index.
     */
    Node<E> node(int index) {
        // assert isElementIndex(index);
        //size >> 1 等于 size/2 
        if (index < (size >> 1)) {
        //index在前半部分，从头开始找
            Node<E> x = first;
            for (int i = 0; i < index; i++)
                x = x.next;
            return x;
        } else {
        //index在后半部分，从尾开始找
            Node<E> x = last;
            for (int i = size - 1; i > index; i--)
                x = x.prev;
            return x;
        }
    }
```
然后再通过E unlink(Node<E> x)删除这个节点：

```
/**
     * Unlinks non-null node x.
     */
    E unlink(Node<E> x) {
        // assert x != null;
        //x节点的元素
        final E element = x.item;
        //后继
        final Node<E> next = x.next;
        //前驱
        final Node<E> prev = x.prev;
        //x前驱为空,删除的节点是头节点
        if (prev == null) {
            first = next;
        } else {
            //x前驱节点的后继节点变为x的后继节点
            prev.next = next;
            //切断前驱连接
            x.prev = null;
        }
        //x后继为空，删除的节点为尾结点
        if (next == null) {
            last = prev;
        } else {
            //x后继节点的前驱变为x的前驱节点
            next.prev = prev;
            //切断后继连接
            x.next = null;
        }
        //删除节点元素置空
        x.item = null;
        size--;
        modCount++;
        return element;
    }
```
- 首先将要删除节点的数据元素、前驱节点，后继节点保存起来。
- 判断删除节点前驱是否为空，如果x前驱为空,则删除的节点是头节点；如果不为空，将x前驱节点的后继节点变为x的后继节点，再通过x.prev = null;切断x节点前驱连接。
- 判断删除节点后继是否为空，如果x后继为空，则删除的节点为尾结点；如果不为空，将x后继节点的前驱变为x的前驱节点，再切断x的后继连接。
- 最后将删除节点元素置空，size减小，modCount增加。

#### get(int index)函数

```
    public E get(int index) {
        //检查索引
        checkElementIndex(index);
        return node(index).item;
    }
```

```
    /**
     * Returns the (non-null) Node at the specified element index.
     */
    Node<E> node(int index) {
        // assert isElementIndex(index);

        if (index < (size >> 1)) {
            Node<E> x = first;
            for (int i = 0; i < index; i++)
                x = x.next;
            return x;
        } else {
            Node<E> x = last;
            for (int i = size - 1; i > index; i--)
                x = x.prev;
            return x;
        }
    }
```
通过node(int index)查找index对应的节点，然后返回对应的数据item。其中size >> 1这个是指size右移一位即size/2 。 
- 当index在前半部分，就从头开始查找

```
            Node<E> x = first;
            for (int i = 0; i < index; i++)
                x = x.next;
            return x;
```

- 当index在后半部分，就从last开始查找

```
            Node<E> x = last;
            for (int i = size - 1; i > index; i--)
                x = x.prev;
            return x;
```


###  六、LinkedList性能相关
LinkedList 是不能随机访问的，按照索引访问效率较低，时间复杂度为复杂度为 O(N/2) 
因此，LinkedList 删除一个节点的时间复杂度为 O(N) ，效率很高。