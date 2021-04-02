package com.example.demo.threadlocal.test;

/**
 * @author zxl
 * @Description: TODO
 * @date 2021/3/31
 */
public class InheritableThreadlocalTest {
    private static InheritableThreadLocal<Integer> requestIdThreadLocal = new InheritableThreadLocal<>();
    public static void main(String[] args) {
        Integer reqId = new Integer(5);
        InheritableThreadlocalTest a = new InheritableThreadlocalTest();
        a.setRequestId(reqId);
    }

    public void setRequestId(Integer requestId) {
        requestIdThreadLocal.set(requestId);
        doBussiness();
    }

    public void doBussiness() {
        System.out.println("首先打印requestId:" + requestIdThreadLocal.get());
        (new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("子线程启动");
                System.out.println("在子线程中访问requestId:" + requestIdThreadLocal.get());
            }
        })).start();
    }


}
