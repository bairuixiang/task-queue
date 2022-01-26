package com.bai.task.queue.exception;

/**
 * @author bairuixiang
 * @date 2021/12/27 9:06 下午
 */
public class TaskQueueException extends RuntimeException {

    public TaskQueueException(String message) {
        super(message);
    }

    public TaskQueueException(Throwable e) {
        super(e);
    }

    public TaskQueueException(String message, Throwable e) {
        super(message,e);
    }
}
