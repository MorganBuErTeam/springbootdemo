package com.test.demo.test;

import com.test.demo.domain.Task;
import com.test.demo.domain.UpKeepInfo;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Description: ${description}
 * @Author: zY
 * @Date: 2020/01/02 16:12
 */
public class TempTest {
    public static void main(String[] args) {
        main4();
    }



    public static void main4() {

        UpKeepInfo upKeepInfo=new UpKeepInfo()
                .setDeviceName("驱动器")
                .setNumDay("3")
                .setCreateDate(new Date());

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) + Integer.valueOf(upKeepInfo.getNumDay()));
        Date today = calendar.getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String result = format.format(today);
        System.out.println(result);

        SimpleDateFormat format2 = new SimpleDateFormat("ss mm HH dd MM ? yyyy"); //cron
        String formatTimeStr = null;
        try {
            if (Objects.nonNull(format.parse(result))) {
                formatTimeStr = format2.format(format.parse(result));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println(formatTimeStr);  //00 00 00 05 02 ? 2020




    }






    public static void main3() {

        long beginTime = System.nanoTime(); //获取纳秒
        System.out.println(beginTime);
        Task task=null;
        Task tempTask=new Task();
        for(int i=0;i<3;i++){
            if(i==0){
                task=new Task();
                task .setId(1);
                task.setState(2);
                task .setTaskName("1");
                task .setTaskType("1");
                task .setAgvCode(1);
                task  .setCreateDate(new Date());
            }
            if(tempTask!=task){
                tempTask=(Task) task.clone();
            }
            if(i==0){
                task.setId(2);
            }
            if(i==1){
                task .setTaskType("2");
            }
        }


    }

    public static void main2() {

        //多个线程同时写HashMap，可能会导致数据的不一致
        Map<Integer,Integer> map=new HashMap<>();
        map.put(1,1);

        /*putVal*/
        //1 public V put(K key, V value) {
        // 2     // 对key的hashCode()做hash
        // 3     return putVal(hash(key), key, value, false, true);
        // 4 }
        // 5
        // 6 final V putVal(int hash, K key, V value, boolean onlyIfAbsent,
        // 7                boolean evict) {
        // 8     Node<K,V>[] tab; Node<K,V> p; int n, i;
        // 9     // 步骤①：tab为空则创建
        //10     if ((tab = table) == null || (n = tab.length) == 0)
        //11         n = (tab = resize()).length;
        //12     // 步骤②：计算index，并对null做处理
        //13     if ((p = tab[i = (n - 1) & hash]) == null)
        //14         tab[i] = newNode(hash, key, value, null);
        //15     else {
        //16         Node<K,V> e; K k;
        //17         // 步骤③：节点key存在，直接覆盖value
        //18         if (p.hash == hash &&
        //19             ((k = p.key) == key || (key != null && key.equals(k))))
        //20             e = p;
        //21         // 步骤④：判断该链为红黑树
        //22         else if (p instanceof TreeNode)
        //23             e = ((TreeNode<K,V>)p).putTreeVal(this, tab, hash, key, value);
        //24         // 步骤⑤：该链为链表
        //25         else {
        //26             for (int binCount = 0; ; ++binCount) {
        //27                 if ((e = p.next) == null) {
        //28                     p.next = newNode(hash, key,value,null);
        //                        //链表长度大于8转换为红黑树进行处理
        //29                     if (binCount >= TREEIFY_THRESHOLD - 1) // -1 for 1st
        //30                         treeifyBin(tab, hash);
        //31                     break;
        //32                 }
        //                    // key已经存在直接覆盖value
        //33                 if (e.hash == hash &&
        //34                     ((k = e.key) == key || (key != null && key.equals(k))))
        //35							break;
        //36                 p = e;
        //37             }
        //38         }
        //39
        //40         if (e != null) { // existing mapping for key
        //41             V oldValue = e.value;
        //42             if (!onlyIfAbsent || oldValue == null)
        //43                 e.value = value;
        //44             afterNodeAccess(e);
        //45             return oldValue;
        //46         }
        //47     }
        //
        //48     ++modCount;
        //49     // 步骤⑥：超过最大容量 就扩容
        //50     if (++size > threshold)
        //51         resize();
        //52     afterNodeInsertion(evict);
        //53     return null;
        //54 }

        /*node节点*/
        //Node是HashMap的一个内部类，实现了Map.Entry接口，本质是就是一个映射(键值对)。
        // static class Node<K,V> implements Map.Entry<K,V> {
        //       final int hash;    //用来定位数组索引位置
        //        final K key;
        //        V value;
        //        Node<K,V> next;   //链表的下一个node
        //
        //        Node(int hash, K key, V value, Node<K,V> next) {
        //            this.hash = hash;
        //            this.key = key;
        //            this.value = value;
        //            this.next = next;
        //        }
        //
        //        public final K getKey()        { return key; }
        //        public final V getValue()      { return value; }
        //        public final String toString() { return key + "=" + value; }
        //
        //        public final int hashCode() {
        //            return Objects.hashCode(key) ^ Objects.hashCode(value);
        //        }
        //
        //        public final V setValue(V newValue) {
        //            V oldValue = value;
        //            value = newValue;
        //            return oldValue;
        //        }
        //
        //        public final boolean equals(Object o) {
        //            if (o == this)
        //                return true;
        //            if (o instanceof Map.Entry) {
        //                Map.Entry<?,?> e = (Map.Entry<?,?>)o;
        //                if (Objects.equals(key, e.getKey()) &&
        //                    Objects.equals(value, e.getValue()))
        //                    return true;
        //            }
        //            return false;
        //        }
        //    }



        //ConcurrentHashMap 线程安全,并发性比Hashtable好
        ConcurrentHashMap<Integer,Integer> concurrentHashMap=new ConcurrentHashMap();
        concurrentHashMap.put(1,2);


    }

    public static void main1() {
        String [] cars = {"A", "B", "C", "D"};
        String [] cars1 = {"1", "2", "3", "4"};

        String a="";
        String temp[]=new String[cars.length];
        for(int i=0;i<cars.length;i++){
            temp[i]=cars[i]+cars1[i];
        }
        System.out.println(Arrays.toString(temp));

        //链式  实体类加注解  @Accessors(chain = true)
        Task task =new Task()
                .setId(1)
                .setState(2)
                .setTaskName("3");


    }



}
