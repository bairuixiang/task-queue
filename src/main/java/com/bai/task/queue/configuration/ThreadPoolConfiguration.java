package com.bai.task.queue.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.concurrent.RejectedExecutionHandler;

/**
 * @author bairuixiang
 * @date 2021/12/21 4:42 下午
 */
@Component
@ConfigurationProperties(prefix = "task.thread.pool")
public class ThreadPoolConfiguration {

    private Integer coreSize = 1;

    private Integer maxSize = 1;

    private Long keepAliveTime = 60L;

    private Class<? extends RejectedExecutionHandler> policy;

    public Integer getCoreSize() {
        return coreSize;
    }

    public void setCoreSize(Integer coreSize) {
        this.coreSize = coreSize;
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
