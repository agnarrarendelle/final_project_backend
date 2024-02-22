package practice.service.job;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import practice.service.TaskService;

@Component
public class ExpireOrderJob {

    @Autowired
    TaskService taskService;

    @Scheduled(cron = "0/60 * * * * ?")
    @Transactional
    public void expireTasks(){
        taskService.expireTasks();
    }

    @Scheduled(cron = "0 0 12 ? * WED")
    @Transactional
    public void deleteExpiredTasks(){
        taskService.deleteExpiredTasks();
    }
}
