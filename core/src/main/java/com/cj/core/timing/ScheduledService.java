package com.cj.core.timing;

import com.cj.core.service.DBService;
import com.cj.core.util.SqlUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ScheduledService {


    @Autowired
    private DBService dbService;

    @Scheduled(cron = "0 0 4 * * *")
    @Async("getAsyncExecutor")
    public void scheduled(){
        log.info("=====>>>>>数据备份");
        dbService.backup();
    }
}
