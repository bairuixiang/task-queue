package com.bai.task.queue.service;

import com.bai.task.queue.context.TaskExecuteContext;

/**
 * @author bairuixiang
 * @date 2021/12/24 10:35 上午
 */
public interface TaskExecutorService {

    /**
     * 任务执行
     * @param taskExecuteContext 任务执行上下文
     */
    void execute(TaskExecuteContext taskExecuteContext);
}

