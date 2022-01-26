package com.bai.task.queue.context;

import com.alibaba.fastjson.JSON;
import com.bai.task.queue.annotation.TaskQueue;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

import java.net.PortUnreachableException;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;


/**
 * @author bairuixiang
 * @date 2021/12/24 10:17 上午
 * 任务执行上下问
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskExecuteContext extends TaskContext  {
    /**
     * 任务的bean class
     */
    private String beanClass;

    /**
     * 任务方法名称
     */
    private String methodName;

    /**
     * 重试的入参，JSON格式
     */
    private String[] params;

    /**
     * 待执行任务参数类型
     */
    private Class<?>[] parameterTypes;


    /**
     * 初始化任务对象
     * @param point 切入点
     */
    public TaskExecuteContext(ProceedingJoinPoint point) {
        MethodSignature methodSignature = (MethodSignature) point.getSignature();
        TaskQueue annotation = methodSignature.getMethod().getAnnotation(TaskQueue.class);
        String[] args = new String[point.getArgs().length];
        for (int i = 0; i < point.getArgs().length; i++) {
            args[i] = JSON.toJSONString(point.getArgs()[i]);
        }
        this.beanClass = point.getTarget().getClass().getName();
        this.methodName = point.getSignature().getName();
        this.params = args;
        this.parameterTypes = methodSignature.getMethod().getParameterTypes();
        setCallBack(annotation.callBack());
        setErrorCallBack(annotation.errorCallBack());
        setTimeUnit(annotation.timeUnit());
        setWaitTime(annotation.waitTime());
        setMethodName(methodSignature.getName());
    }
}
