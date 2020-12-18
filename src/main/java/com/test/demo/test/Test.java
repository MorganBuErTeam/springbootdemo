package com.test.demo.test;

import com.test.demo.domain.City;
import com.test.demo.domain.Task;
import org.springframework.core.SpringVersion;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.*;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Description: ${description}
 * @Author: zY
 * @Date: 2019/08/14 10:32
 */
public class Test {


    public static void main(String[] args) {
        Test.main7();
    }

    public static void main7() {
        City city=new City("上海市");
        city.setCid(1);
        City city1=new City("上海市");
        System.out.println(city==city1);
        System.out.println(city.equals(city1));

    }

    //写一个 ArrayList 的动态代理类
    public static void main6() {
        final  List<String> list=new ArrayList<>();
        List<String> proxyInstance=(List<String>) Proxy.newProxyInstance(list.getClass().getClassLoader(), list.getClass().getInterfaces(), new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                return method.invoke(list,args);
            }
        });
        proxyInstance.add("你好");
        System.out.println(list);
    }






    // 控制某个方法允许并发访问线程的个数
    //1.构造函数创建了一个 Semaphore 对象，并且初始化了 5 个信号。这样的效果是控制test方法最多只能有5个线程并发访问
    static Semaphore semaphore = new Semaphore(5, true);
    //2.
    public static void test() {
        try {
            //申请一个请求
            semaphore.acquire();
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + "进来了");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + "走了");
        semaphore.release();
    }
    //3.
//    public static void main(String[] args) {
//        for (int i = 0; i < 100; i++) {
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    Test();
//                }
//            }).start();
//        }
//    }





    public static void main5() {
        AtomicBoolean atomicBoolean = new AtomicBoolean(false);
//        boolean expectedValue = false;
//        boolean newValue = true;
//        //compareAndSet方法：如果atomicBoolean==expectedValue，把newValue的值赋给atomicBoolean；
//        boolean wasNewValueSet = atomicBoolean.compareAndSet(expectedValue, newValue);

        AtomicInteger atomicInteger = new AtomicInteger(123);
        //get() 方法获取 AtomicInteger 实例的值
        int theValue = atomicInteger.get();
        //set() 方法对 AtomicInteger 的值进行重新设置
        atomicInteger.set(234);
        int expectedValue = 123;
        int newValue = 234;
        atomicInteger.compareAndSet(expectedValue, newValue);
        String a = "";
    }

    public static void main4() {
        HashMap hashMap = new HashMap();
        hashMap.put(null, null);
        Hashtable hashtable = new Hashtable();
        hashtable.put(null, null);
        String a = "";
    }

    public static void main3() {
        Queue<String> queue = new LinkedList<String>(); //a 队列
        String ahgfj = queue.poll();
        Queue<String> queue2 = new LinkedList<String>(); //b 队列
        ArrayList<String> a = new ArrayList<String>(); //arrylist 集合是中间参数
        //往 a 队列添加元素
        queue.offer("a");
        queue.offer("b");
        queue.offer("c");
        queue.offer("d");
        queue.offer("e");
        System.out.print("进栈：");
        //a 队列依次加入 list 集合之中
        for (String q : queue) {
            a.add(q);
            System.out.print(q);
        }
        //以倒序的方法取出（a 队列依次加入 list 集合）之中的值，加入 b 对列
        for (int i = a.size() - 1; i >= 0; i--) {
            queue2.offer(a.get(i));
        }
        //打印出栈队列
        System.out.println("");
        System.out.print("出栈：");
        for (String q : queue2) {
            System.out.print(q);
        }
    }

    public static void main1() {
        String a = "AB";
        String b = "A" + "B";
        String c = new String("AB");
        System.out.println("a==b 结果：" + (a == b));
        System.out.println("a==c 结果：" + (a == c));
        System.out.println("b==c 结果：" + (b == c));
        System.out.println("a.equals(b) 结果：" + a.equals(b));
        System.out.println("a.equals(c) 结果：" + a.equals(c));
        System.out.println("b.equals(c) 结果：" + b.equals(c));
        //a==b 结果：true
        //a==c 结果：false
        //b==c 结果：false
        //a.equals(b) 结果：true
        //a.equals(c) 结果：true
        //b.equals(c) 结果：true
    }

    //springboot版本号
    public static void main2() {
        System.out.println(SpringVersion.getVersion());
    }
}
