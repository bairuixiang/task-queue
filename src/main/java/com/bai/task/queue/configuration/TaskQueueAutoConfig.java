package com.bai.task.queue.configuration;

import com.bai.task.queue.context.TaskExecuteContext;
import com.bai.task.queue.policy.DelayedQueuePolicy;
import com.bai.task.queue.policy.RedisTaskQueuePolicy;
import com.bai.task.queue.policy.TaskQueueHandler;

import org.redisson.Redisson;
import org.redisson.config.Config;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import java.util.concurrent.DelayQueue;

/**
 * @author bairuixiang
 * @date 2021/12/21 10:36 上午
 */
@ComponentScan(
        basePackages = {"com.bai.task.queue"}
)
public class TaskQueueAutoConfig {

    private static final String REDIS_URL_PREFIX = "redis://";

    /**
     * redis
     */
    @ConditionalOnProperty(name = "bai.task.queue.type", havingValue = "redis")
    @Bean
    public TaskQueueHandler redisTaskQueueHandler() {
        return new RedisTaskQueuePolicy();
    }

    /**
     * mysql
     */
    @ConditionalOnProperty(name = "bai.task.queue.type", havingValue = "mysql")
    @Bean
    public TaskQueueHandler mysqlTaskQueueHandler() {
        return new RedisTaskQueuePolicy();
    }


    /**
     * 默认队列
     */
    @ConditionalOnMissingBean(TaskQueueHandler.class)
    @Bean
    public TaskQueueHandler defaultTaskQueueHandler() {
        DelayedQueuePolicy delayedQueuePolicy = new DelayedQueuePolicy();
        DelayQueue<TaskExecuteContext> taskExecuteContexts = new DelayQueue<>();
        delayedQueuePolicy.setDelayQueue(taskExecuteContexts);
        return delayedQueuePolicy;
    }


    @ConditionalOnProperty(name = "bai.task.queue.type", havingValue = "redis")
    @ConditionalOnMissingBean(Redisson.class)
    @Bean
    public Redisson redisson(RedisProperties redisProperties){
        Config config = new Config();
        config.useSingleServer().setAddress(REDIS_URL_PREFIX + redisProperties.getHost()+":"+redisProperties.getPort());
        config.useSingleServer().setPassword(redisProperties.getPassword());
        return (Redisson) Redisson.create(config);
    }


}
