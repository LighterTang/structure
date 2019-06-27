## Java集合类-概述

####  集合概述
Java集合分为Set、List、Queue、Map四种体系。Set代表无序、不可重复的集合；List代表有序、重复的集合；而Map则代表具有映射关系的集合；Queue体系集合，代表一种队列集合实现。

#### Collection集合体系继承树
![](https://github.com/LighterTang/compress-picture/blob/master/picture/collection.png?raw=true)

####  Map体系继承树
![](https://github.com/LighterTang/compress-picture/blob/master/picture/map.png?raw=true)

#### Collection接口方法
Collection接口是Set、List和Queue接口的父接口，定义了如下操作：   
1. boolean add(Object o)：增加一个元素
2. boolean addAll(Collection c)：将集合c添加到指定集合里
3. void clear()：清除所有元素，长度变为0
4. boolean contains(Object o)：集合是否包含指定元素
5. boolean containsAll(Collection c)：集合是否包含集合c中的所有元素
6. Iterator iterator()：返回Iterator对象，用于遍历集合中的元素
7. boolean remove(Object o)：移除指定元素o,如果有多个o时只删除第一个
8. boolean removeAll(Collection c)：相当于减集合c
9. boolean retainAll(Collection c)：相当于求与c的交集
10. int size()：返回元素个数
11. Object[] toArray()：把集合转换为一个数组
12. ListIterator<E> listIterator();允许向前、向后两个方向遍历 List;

#### Map接口方法
1. V put(K key, V value): 将指定的值与此映射中的指定键相关联（可选操作）。如果此映射中以前包含一个该键的映射关系，则用指定值替换旧值。
2. boolean containsKey(Object key): 如果此映射包含指定键的映射关系，则返回 true。
3. boolean containsValue(Object value): 如果此映射为指定值映射一个或多个键，则返回 true。
4. Set<Map.Entry<K,V>> entrySet(): 返回此映射中包含的映射关系的 set 视图。
5. V get(Object key): 返回指定key对应的value
6. V remove(Object key): 删除指定key对应的key-value对
7. int size(); 大小
8. boolean isEmpty(); 判空
9. void putAll(Map<? extends K, ? extends V> m);
10. void clear();

#### 遍历集合

```
public static void main(String[] args) {
        //for循环
        List<String> books = new ArrayList<>();
        books.add("bom");
        books.add("tag");
        books.add("ret");
        for (String s : books) {
            System.out.println(s);
        }

        //iterator遍历
        Iterator<String> it = books.iterator();
        while (it.hasNext()){
            String book = it.next();
            System.out.println(book);
            if ("1".equals(book)){
                //移除it.next()元素
                it.remove();

                //java.util.ConcurrentModificationException
                //books.remove(book);
            }
        }
        //lambda表达式遍历
        books.forEach(book -> System.out.println("书名："+book));
   
        //iterator.forEachRemaining
        Iterator<String> iterator = books.iterator();
        iterator.forEachRemaining(book -> System.out.println("图书="+book));
    }
```
1. for循环遍历
2. iterator遍历

集合在遍历过程中使用List的remove(Object o)方法会引发异常：java.util.ConcurrentModificationException

3. lambda表达式遍历（Iterable接口）

 因为Collection的父接口是Iterable，Java8中Iterable接口有一个forEach默认方法。参数类型是函数式接口Consumer。因此可以使用lambda表达式遍历。



```
    default void forEach(Consumer<? super T> action) {
        Objects.requireNonNull(action);
        for (T t : this) {
            action.accept(t);
        }
    }
```
4.lambda表达式遍历（Iterator接口）

```
default void forEachRemaining(Consumer<? super E> action) {
        Objects.requireNonNull(action);
        while (hasNext())
            action.accept(next());
    }
```

#### 