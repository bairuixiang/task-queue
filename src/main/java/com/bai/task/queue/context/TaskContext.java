package com.bai.task.queue.context;
import lombok.Data;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

/**
 * @author bairuixiang
 * @date 2021/12/24 10:07 上午
 * 任务上下文
 */
@Data
public class TaskContext implements Serializable {

    /**
     * 等待时间
     */
    private Long waitTime;

    /**
     * 时间类型
     */
    private TimeUnit timeUnit;
    /**
     * 当前异常
     */
    private Throwable currentThrowable;

    /**
     * 成功回调
     */
    private String callBack;

    /**
     * 异常回调
     */
    private String errorCallBack;
}
