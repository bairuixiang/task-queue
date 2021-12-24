package com.bai.task.queue.aop;

import com.bai.task.queue.common.constant.TaskConstants;
import com.bai.task.queue.context.TaskExecuteContext;
import com.bai.task.queue.policy.TaskQueueHandler;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author bairuixiang
 * @date 2021/12/24 11:18 上午
 * 延迟任务切面
 */
@Slf4j
@Aspect
@Component
public class TaskQueueAspect {

    @Resource
    private TaskQueueHandler taskQueueHandler;

    @Pointcut("@annotation(com.bai.task.queue.annotation.TaskQueue)")
    public void taskQueuePointcut() {

    }

    @Around("taskQueuePointcut()")
    public Object api(ProceedingJoinPoint point) {
        Boolean flag = TaskConstants.ASYNC_OPERATE_TYPE.get();
        if(flag != null && flag){
            try {
                return point.proceed();
            } catch (Throwable throwable) {
               log.error("执行任务异常");
            }
        }
        TaskExecuteContext taskExecuteContext = new TaskExecuteContext(point);
        taskQueueHandler.execute(taskExecuteContext, taskExecuteContext.getWaitTime(), taskExecuteContext.getTimeUnit());
        return null;
    }


}
