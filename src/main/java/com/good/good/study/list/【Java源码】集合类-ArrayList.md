#### 一、类继承关系

```
public class ArrayList<E> extends AbstractList<E>
        implements List<E>, RandomAccess, Cloneable, java.io.Serializable
```
ArrayList继承AbstractList,也实现了List和RandomAccess(一个空接口，也就是标记接口。)，Cloneable(可被克隆), Serializable接口。

#### 二、类属性

```
    //默认容量
    private static final int DEFAULT_CAPACITY = 10;

    //空对象数组(initialCapacity == 0时返回)
    private static final Object[] EMPTY_ELEMENTDATA = {};

    //调用无参构造方法，返回的是该数组
    private static final Object[] DEFAULTCAPACITY_EMPTY_ELEMENTDATA = {};

    //元素数组，保存添加到ArrayList中的元素
    transient Object[] elementData;
    
    //ArrayList大小
    private int size;
```

 elementData是transient修饰，所以elementData不能被序列化。但是ArrayList又是可以被序列化的，这是为何？

-  因为ArrayList有两个方法writeObject、readObject用于序列化和反序列化。elementData是transient修饰是为了避免ArrayList扩容（1.5倍）导致的空间浪费

 1. 序列化的时候elementData的数据会被写到ObjectOutputStream中
 
```
private void writeObject(java.io.ObjectOutputStream s)
        throws java.io.IOException{
        // Write out element count, and any hidden stuff
        int expectedModCount = modCount;
        s.defaultWriteObject();

        // Write out size as capacity for behavioural compatibility with clone()
        s.writeInt(size);

        // Write out all elements in the proper order.
        for (int i=0; i<size; i++) {
            s.writeObject(elementData[i]);
        }

        if (modCount != expectedModCount) {
            throw new ConcurrentModificationException();
        }
    }
```

 2. 反序列化的时候会从ObjectInputStream读取出来
 
```
private void readObject(java.io.ObjectInputStream s)
        throws java.io.IOException, ClassNotFoundException {
        elementData = EMPTY_ELEMENTDATA;

        // Read in size, and any hidden stuff
        s.defaultReadObject();

        // Read in capacity
        s.readInt(); // ignored

        if (size > 0) {
            // be like clone(), allocate array based upon size not capacity
            ensureCapacityInternal(size);

            Object[] a = elementData;
            // Read in all elements in the proper order.
            for (int i=0; i<size; i++) {
                a[i] = s.readObject();
            }
        }
    }
```
#### 三、构造函数
- 指定容量

```
    public ArrayList(int initialCapacity) {
        if (initialCapacity > 0) {
            this.elementData = new Object[initialCapacity];
        } else if (initialCapacity == 0) {
            //空对象数组
            this.elementData = EMPTY_ELEMENTDATA;
        } else {
            throw new IllegalArgumentException("Illegal Capacity: "+
                                               initialCapacity);
        }
    }
```

- 默认

```
    public ArrayList() {
        this.elementData = DEFAULTCAPACITY_EMPTY_ELEMENTDATA;
    }
```

- ArrayList(Collection<? extends E> c)

```
    public ArrayList(Collection<? extends E> c) {
        elementData = c.toArray();
        if ((size = elementData.length) != 0) {
            if (elementData.getClass() != Object[].class)
                elementData = Arrays.copyOf(elementData, size, Object[].class);
        } else {
            //空对象数组
            this.elementData = EMPTY_ELEMENTDATA;
        }
    }
```
#### 四、ArrayList如何扩容


```
    public boolean add(E e) {
        //确保elementData数组的大小
        ensureCapacityInternal(size + 1);
        //在结尾处添加元素
        elementData[size++] = e;
        return true;
    }
```
上面是add(E e)方法的源码，在添加元素前会调用 ensureCapacityInternal(size + 1);确保elementData数组的大小。


```
    private void ensureCapacityInternal(int minCapacity) {
        // 判断元素数组是否为空数组
        if (elementData == DEFAULTCAPACITY_EMPTY_ELEMENTDATA) {
            //minCapacity最少=DEFAULT_CAPACITY=10
            minCapacity = Math.max(DEFAULT_CAPACITY, minCapacity);
        }

        ensureExplicitCapacity(minCapacity);
    }
```
- 其中ensureCapacityInternal的源码片段首先判断elementData是否为无参构造器的空数组，如果是的话，Math.max（）取较大值赋值给minCapacity，最小是10（默认容量）。然后，调用ensureExplicitCapacity：

```
    private void ensureExplicitCapacity(int minCapacity) {
        // List结构性修改加1
        modCount++;
        //minCapacity大于elementData数组长度
        if (minCapacity - elementData.length > 0)
            //增长
            grow(minCapacity);
    }

```

- ensureExplicitCapacity中modCount记录List结构性变化次数（结构性变化，在迭代的过程中可能会造成错误结果）。
- 紧接着，如果minCapacity大于elementData数组长度则调用 grow(minCapacity)增长函数

```
    
    private void grow(int minCapacity) {
        // overflow-conscious code
        int oldCapacity = elementData.length;
        //oldCapacity >> 1右移一位，这里相当于扩容1.5倍
        int newCapacity = oldCapacity + (oldCapacity >> 1);
        if (newCapacity - minCapacity < 0)
            newCapacity = minCapacity;
        if (newCapacity - MAX_ARRAY_SIZE > 0)
            newCapacity = hugeCapacity(minCapacity);
        // minCapacity is usually close to size, so this is a win:
        elementData = Arrays.copyOf(elementData, newCapacity);
    }
```
上面的扩容方法中 **oldCapacity >> 1**  表明elementData.length右移一位，扩容后的容量为原始容量的1.5倍

#### 五、主要函数

##### remove()函数

```
    public E remove(int index) {
        rangeCheck(index);

        modCount++;
        E oldValue = elementData(index);
        //需要移动的元素个数
        int numMoved = size - index - 1;
        //删除的不是最后一个元素
        if (numMoved > 0)
            //index后的元素都向前移动一位
            System.arraycopy(elementData, index+1, elementData, index, numMoved);
        //size减一，释放引用，方便垃圾回收器进行垃圾回收
        elementData[--size] = null; // clear to let GC do its work

        return oldValue;
    }
```
- remove(int index)方法会先计算需要移动的元素个数，index后的元素都向前移动一位（调用System.arraycopy方法移动），然后size减一，赋值为null释放引用，方便垃圾回收器进行垃圾回收。
##### 单线程环境下，正确的删除元素，应该使用iterator()迭代器删除元素。

例如要删除集合中的“a”
```
        Iterator<String> iterator = list.iterator();
        while (iterator.hasNext()){
            if ("a".equals(iterator.next())) {
                iterator.remove();
            }
        }
```
或者使用lambda表达式

```
list.removeIf("x"::equals);
```


##### set()函数,会替换新值，返回旧值。
```
    public E set(int index, E element) {
        //检查索引
        rangeCheck(index);

        E oldValue = elementData(index);
        elementData[index] = element;
        //返回旧值
        return oldValue;
    }
```
##### get()函数，获取元素

```
    public E get(int index) {
        rangeCheck(index);

        return elementData(index);
    }
```

#### 六、ArrayList性能相关
- 在能够确定ArrayList容量大小的情况下尽量预估容量，调用构造器public ArrayList(int initialCapacity)指定大小，避免因为自动扩容带来的性能开销。
- 使用trimToSize()使ArrayList的内部数组大小减少为实际大小，以此节省空间

```
    public void trimToSize() {
        modCount++;
        if (size < elementData.length) {
            elementData = (size == 0)
              ? EMPTY_ELEMENTDATA
              : Arrays.copyOf(elementData, size);
        }
    }
```
