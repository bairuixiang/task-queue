package com.bai.task.queue.consumer;

import com.bai.task.queue.common.constant.TaskConstants;
import com.bai.task.queue.configuration.TaskConfig;
import com.bai.task.queue.configuration.ThreadPoolConfiguration;
import com.bai.task.queue.context.TaskExecuteContext;
import com.bai.task.queue.policy.TaskQueueHandler;
import com.bai.task.queue.service.TaskExecutorService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author bairuixiang
 * @date 2021/12/23 4:43 下午
 */
@Slf4j
@Component
public class TaskConsumer {

    @Resource
    private TaskQueueHandler taskQueueHandler;

    @Resource
    private TaskConfig taskConfig;

    @Resource
    private ThreadPoolConfiguration poolConfig;

    @Resource
    private TaskExecutorService taskExecutorService;

    private ThreadPoolExecutor executor;

    /**
     * 初始化加载线程池
     */
    @PostConstruct
    public void initThreadPool() throws InstantiationException, IllegalAccessException {
        RejectedExecutionHandler rejectedExecutionHandler = poolConfig.getPolicy().newInstance();
        executor = new ThreadPoolExecutor(
                poolConfig.getCoreSize(),
                poolConfig.getMaxSize(),
                poolConfig.getKeepAliveTime(),
                TimeUnit.SECONDS,
                poolConfig.getQueue(), new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                Thread thread = new Thread(r);
                thread.setName(poolConfig.getThreadPoolName());
                return thread;
            }
        }, rejectedExecutionHandler);
        executor.execute(this::run);
    }

    /**
     * 监听器
     */
    public void run() {
        while (true) {
            try {
                TaskExecuteContext take = taskQueueHandler.take(taskConfig.getQueueName());
                if (ObjectUtils.isNotEmpty(take)) {
                    executor.execute(() -> taskExecutorService.execute(take));
                }
            } catch (Exception e) {
                log.error("队列执行异常", e);
            }
        }
    }

}
