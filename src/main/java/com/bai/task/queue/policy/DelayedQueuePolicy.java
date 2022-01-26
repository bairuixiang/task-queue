package com.bai.task.queue.policy;

import com.bai.task.queue.context.TaskExecuteContext;
import org.springframework.lang.NonNull;

import java.util.concurrent.DelayQueue;
import java.util.concurrent.TimeUnit;

/**
 * @author bairuixiang
 * @date 2021/12/27 4:48 下午
 */
public class DelayedQueuePolicy implements TaskQueueHandler{

    /**
     * 延迟队列
     */
    private static DelayQueue<TaskExecuteContext> delayQueue;


    public void setDelayQueue(@NonNull DelayQueue<TaskExecuteContext> delayQueue){
        DelayedQueuePolicy.delayQueue = delayQueue;
    }
    /**
     * 提交任务
     *
     * @param task     任务
     * @param delay    延迟时间
     * @param timeUnit 时间类型
     */
    @Override
    public void execute(TaskExecuteContext task, long delay, TimeUnit timeUnit) {
        delayQueue.offer(task,delay,timeUnit);
    }

    /**
     * 获取任务
     *
     * @param queueNam 队列名
     * @return 任务
     */
    @Override
    public TaskExecuteContext take(String queueNam) {
        try {
            return delayQueue.take();
        } catch (InterruptedException exception) {
            exception.printStackTrace();
        }
        return null;
    }

}
