package com.bai.task.queue.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author bairuixiang
 * @date 2021/12/21 2:54 下午
 */
@Component
@ConfigurationProperties(prefix = "bai.task.queue")
public class TaskConfig {

    /**
     * 任务等待时间
     */
    private Long waitTime = 1000L;

    /**
     * 每次执行任务
     */
    private Integer execute = 1;

    /**
     * 队列长度
     */
    private Integer queueSize = 5;

    /**
     * 队列名
     */
    private String queueName = "task-queue";

    public TaskConfig() {
    }

    public TaskConfig(Long waitTime, Integer execute, Integer queueSize) {
        this.waitTime = waitTime;
        this.execute = execute;
        this.queueSize = queueSize;
    }

    public Long getWaitTime() {
        return waitTime;
    }

    public String getQueueName() {
        return queueName;
    }

    public void setQueueName(String queueName) {
        this.queueName = queueName;
    }

    public void setWaitTime(Long waitTime) {
        this.waitTime = waitTime;
    }

    public Integer getExecute() {
        return execute;
    }

    public void setExecute(Integer execute) {
        this.execute = execute;
    }

    public Integer getQueueSize() {
        return queueSize;
    }

    public void setQueueSize(Integer queueSize) {
        this.queueSize = queueSize;
    }
}
