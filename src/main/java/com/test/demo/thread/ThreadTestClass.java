package com.test.demo.thread;

import com.test.demo.domain.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

//创建线程池,使用多线程
public class ThreadTestClass {

    public static class TestAGVRoute implements  Callable<Task>{
        private String name;
        private Integer count;
        private CountDownLatch countDownLatch;

        public TestAGVRoute(String name,Integer count,CountDownLatch countDownLatch){
            this.name = name;
            this.count = count;
            this.countDownLatch = countDownLatch;
        }

        @Override
        public Task call() throws Exception {
            System.out.println(Thread.currentThread().getName()+"子线程开始处理");
            Thread.sleep(5000);
            //计数器-1
            countDownLatch.countDown();
            Task task = new Task();
            task.setTaskName("参数是"+name);
            return task;
        }
    }


    public static void main(String[] args) {
        //创建线程池
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        //计数器
        CountDownLatch countDownLatch = new CountDownLatch(10);
        List<Future<Task>> resultList = new ArrayList<>();
        for (int i = 0;i<10;i++){
            /** 有返回值 */
            Future<Task> result = executorService.submit(new TestAGVRoute("name="+i,i,countDownLatch));
            resultList.add(result);

            /** 无返回值 */
//            executorService.execute(new Runnable() {
//                @Override
//                public void run() {
//                    try {
//                        System.out.println(Thread.currentThread().getName()+"子线程开始处理");
//                        Thread.sleep(5000);
//                        //计数器-1
//                        countDownLatch.countDown();
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//            });
        }
        try {
            //关闭线程池
            executorService.shutdown();
            System.out.println("主线程挂起，等待计数器归零");
            //主线程挂起，等待计数器归零
            countDownLatch.await();
            System.out.println("子线程处理完毕");
            for (Future<Task> stringFuture : resultList) {
                Task result = stringFuture.get();
                System.out.println(result.getTaskName());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
