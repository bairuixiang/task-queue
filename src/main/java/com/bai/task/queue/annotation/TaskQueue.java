package com.bai.task.queue.annotation;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

/**
 * @author bairuixiang
 * @date 2021/12/24 10:07 上午
 * 延迟任务
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TaskQueue {

    /**
     * 等待时间
     */
    long waitTime();

    /**
     * 时间类型
     */
    TimeUnit timeUnit() default TimeUnit.SECONDS;

    /**
     * 异常回调通知
     */
    String errorCallBack() default "";

    /**
     * 成功通知
     */
    String callBack() default "";
}
