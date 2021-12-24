package com.bai.task.queue.policy;

import com.bai.task.queue.context.TaskExecuteContext;

import java.util.concurrent.TimeUnit;

/**
 * @author bairuixiang
 * @date 2021/12/21 2:04 下午
 * 任务队列
 */
public interface TaskQueueHandler {
    /**
     * 提交任务
     *
     * @param task     任务
     * @param delay    延迟时间
     * @param timeUnit 时间类型
     */
     void execute(TaskExecuteContext task, long delay, TimeUnit timeUnit);

    /**
     * 获取任务
     *
     * @param queueNam 队列名
     * @return 任务
     */
    TaskExecuteContext take(String queueNam);


}
