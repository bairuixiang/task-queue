package com.bai.task.queue.policy;

import com.bai.task.queue.configuration.TaskConfig;
import com.bai.task.queue.context.TaskExecuteContext;
import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RBlockingQueue;
import org.redisson.api.RDelayedQueue;
import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @author bairuixiang
 * @date 2021/12/21 2:53 下午
 */
@Slf4j
public class RedisTaskQueuePolicy implements TaskQueueHandler{

    @Resource
    private Redisson redisson;

    @Resource
    private TaskConfig taskConfig;
    /**
     * 提交任务
     *
     * @param task     任务
     * @param delay    延迟时间
     * @param timeUnit 时间类型
     */
    @Override
    public void execute(TaskExecuteContext task, long delay, TimeUnit timeUnit) {
        RBlockingQueue<TaskExecuteContext> blockingQueue = redisson.getBlockingQueue(taskConfig.getQueueName());
        RDelayedQueue<TaskExecuteContext> delayedQueue = redisson.getDelayedQueue(blockingQueue);
        delayedQueue.offer(task,delay,timeUnit);
    }

    /**
     * 获取任务
     *
     * @param queueNam 队列名
     * @return 任务
     */
    @Override
    public TaskExecuteContext take(String queueNam) {
        RBlockingQueue<TaskExecuteContext> blockingQueue = redisson.getBlockingQueue(queueNam);
        try {
            log.info("获取队列任务");
            return blockingQueue.take();
        } catch (InterruptedException exception) {
            log.error("获取任务异常",exception);
        }
        return null;
    }
}
