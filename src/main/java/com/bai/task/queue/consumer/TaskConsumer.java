package com.bai.task.queue.consumer;

import com.bai.task.queue.common.constant.TaskConstants;
import com.bai.task.queue.configuration.TaskConfig;
import com.bai.task.queue.context.TaskExecuteContext;
import com.bai.task.queue.policy.TaskQueueHandler;
import com.bai.task.queue.service.TaskExecutorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;

/**
 * @author bairuixiang
 * @date 2021/12/23 4:43 下午
 */
@Slf4j
@Component
public class TaskConsumer implements CommandLineRunner {

    @Resource
    private TaskQueueHandler taskQueueHandler;

    @Resource
    private TaskConfig taskConfig;

    @Resource
    private TaskExecutorService taskExecutorService;

    @Override
    public void run(String... args) throws Exception {
        //初始化消费者
        while (true) {
            try {
                TaskConstants.ASYNC_OPERATE_TYPE.set(true);
                TaskExecuteContext take = taskQueueHandler.take(taskConfig.getQueueName());
                log.info("获取到任务，开始执行 {}",take);
                taskExecutorService.execute(take);
            } catch (Exception e) {
                log.error("队列执行异常", e);
            }
        }
    }

}
