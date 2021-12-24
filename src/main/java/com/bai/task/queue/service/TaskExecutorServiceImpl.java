package com.bai.task.queue.service;

import com.alibaba.fastjson.JSON;
import com.bai.task.queue.common.context.SpringContextHolder;
import com.bai.task.queue.context.TaskExecuteContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;

/**
 * @author bairuixiang
 * @date 2021/12/24 10:30 上午
 * 延迟任务执行引擎
 */
@Slf4j
@Service
public class TaskExecutorServiceImpl implements TaskExecutorService {


    /**
     * 任务执行
     *
     * @param taskExecuteContext 任务执行上下文
     */
    @Override
    public void execute(TaskExecuteContext taskExecuteContext) {
        try {
            //获取执行类
            Object bean = getBean(taskExecuteContext);
            //获取执行方法
            Method method = getMethod(taskExecuteContext, bean);
            //获取执行参数
            Object[] params = getParams(taskExecuteContext);
            Object invoke = method.invoke(bean, params);
            //TODO 执行成功回调
        } catch (Exception e) {
            log.error("延迟任务执行出现异常",e);
            //TODO 执行异常回调
        }
    }


    /**
     * 获取bean
     *
     * @param taskExecuteContext 任务执行上下问题
     * @return 需要执行的任务所在的类
     * @throws ClassNotFoundException 找不到类异常
     */
    private Object getBean(TaskExecuteContext taskExecuteContext) throws ClassNotFoundException {
        return SpringContextHolder.getBean(
                this.getClass().getClassLoader().loadClass(taskExecuteContext.getBeanClass()));
    }

    /**
     * 获取方法
     *
     * @param taskExecuteContext 任务执行上下文
     * @param beanObject         任务类
     * @return method 需要执行的方法
     * @throws NoSuchMethodException 找不到方法异常
     */
    private Method getMethod(TaskExecuteContext taskExecuteContext, Object beanObject) throws NoSuchMethodException {
        return beanObject.getClass().getDeclaredMethod(taskExecuteContext.getMethodName(), taskExecuteContext.getParameterTypes());
    }

    /**
     * 获取方法执行参数
     *
     * @param taskExecuteContext 任务执行上下问题
     * @return 参数列表
     */
    private Object[] getParams(TaskExecuteContext taskExecuteContext) {
        Object[] params = new Object[taskExecuteContext.getParams().length];
        for (int i = 0; i < taskExecuteContext.getParams().length; i++) {
            params[i] = JSON.parseObject(taskExecuteContext.getParams()[i], taskExecuteContext.getParameterTypes()[i]);
        }
        return params;
    }
}
