package com.computinglife.loverface.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 异步任务工具
 * Created by youngliu on 12/2/15.
 */
public class AsyncTaskUtil {
    private static ExecutorService service;

    /**
     * 初始化线程池
     */
    public static void init() {
        service = Executors.newCachedThreadPool();
    }

    /**
     * 关闭线程池，调用shutdownNow，未完成的任务继续运行，队列中的任务不再执行
     */
    public static void destory() {
        if (service != null && !service.isShutdown()) {
            service.shutdownNow();
            service = null;
        }
    }

    /**
     * 添加一个任务
     *
     * @param runnable
     *            实现java.lang.Runnable接口的任务
     */
    public static void addTask(Runnable runnable) {
        if (service == null) {
            init();
        }
        if (service != null && !service.isShutdown()) {
            synchronized (service) {
                service.execute(runnable);
            }
        }
    }
}
