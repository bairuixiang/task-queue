package com.bai.task.queue.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author bairuixiang
 * @date 2021/12/21 4:42 下午
 */
@Component
@ConfigurationProperties(prefix = "task.thread.pool")
public class ThreadPoolConfiguration {

    /**
     * 核心执行线程数量
     */
    private Integer coreSize = 5;

    /**
     * 最大线程数量
     */
    private Integer maxSize = 10;

    /**
     * 线程存活时间
     */
    private Long keepAliveTime = 60L;

    /**
     * 队列最大长度
     */
    private Integer queueSize = 20;

    /**
     * 线程队列
     */
    private BlockingQueue<Runnable> queue;

    /**
     * 线程池名称
     */
    private String threadPoolName = "delay-execute";


    public String getThreadPoolName() {
        return threadPoolName;
    }

    public void setThreadPoolName(String threadPoolName) {
        this.threadPoolName = threadPoolName;
    }

    /**
     * 拒绝策略
     */
    private Class<? extends RejectedExecutionHandler> policy = ThreadPoolExecutor.CallerRunsPolicy.class;

    public Integer getCoreSize() {
        return coreSize;
    }

    public void setCoreSize(Integer coreSize) {
        this.coreSize = coreSize;
    }

    public Integer getQueueSize() {
        return queueSize;
    }

    public void setQueueSize(Integer queueSize) {
        this.queue = new LinkedBlockingQueue<>(queueSize);
        this.queueSize = queueSize;
    }

    public BlockingQueue<Runnable> getQueue() {
        if (queue == null) {
            this.queue = new LinkedBlockingQueue<>(queueSize);
        }
        return queue;
    }

    public void setQueue(BlockingQueue<Runnable> queue) {
        this.queue = queue;
    }

    public Integer getMaxSize() {
        return maxSize;
    }

    public void setMaxSize(Integer maxSize) {
        this.maxSize = maxSize;
    }

    public Long getKeepAliveTime() {
        return keepAliveTime;
    }

    public void setKeepAliveTime(Long keepAliveTime) {
        this.keepAliveTime = keepAliveTime;
    }

    public Class<? extends RejectedExecutionHandler> getPolicy() {
        return policy;
    }

    public void setPolicy(Class<? extends RejectedExecutionHandler> policy) {
        this.policy = policy;
    }
}
