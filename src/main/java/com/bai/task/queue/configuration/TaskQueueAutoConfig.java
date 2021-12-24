package com.bai.task.queue.configuration;

import com.bai.task.queue.policy.RedisTaskQueuePolicy;
import com.bai.task.queue.policy.TaskQueueHandler;

import org.redisson.Redisson;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.util.StringUtils;

/**
 * @author bairuixiang
 * @date 2021/12/21 10:36 上午
 */
@ComponentScan(
        basePackages = {"com.bai.task.queue"}
)
public class TaskQueueAutoConfig {

    @Value("${spring.redis.host:127.0.0.1}:${spring.redis.port:6379}")
    private String redisAddress;

    private static final String REDIS_URL_PREFIX = "redis://";

    @ConditionalOnProperty(name = "bai.task.queue.type", havingValue = "redis")
    @Bean
    public TaskQueueHandler redisTaskQueueHandler() {
        RedisTaskQueuePolicy redisTaskQueuePolicy = new RedisTaskQueuePolicy();


        return new RedisTaskQueuePolicy();
    }


    @ConditionalOnProperty(name = "bai.task.queue.type", havingValue = "mysql")
    @Bean
    public TaskQueueHandler mysqlTaskQueueHandler() {
        return new RedisTaskQueuePolicy();
    }


    @ConditionalOnMissingBean(TaskQueueHandler.class)
    @Bean
    public TaskQueueHandler defaultTaskQueueHandler() {
        return new RedisTaskQueuePolicy();
    }


    @ConditionalOnMissingBean(Redisson.class)
    @Bean
    public Redisson redisson(){
        Config config = new Config();
        config.useSingleServer().setAddress(REDIS_URL_PREFIX + redisAddress);
        return (Redisson) Redisson.create(config);
    }


}
