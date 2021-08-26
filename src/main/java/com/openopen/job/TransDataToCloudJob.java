package com.openopen.job;

import com.openopen.service.SHT20MST01Service;
import lombok.SneakyThrows;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class TransDataToCloudJob implements Job {

    //Logger
    private static final Logger logger = LoggerFactory.getLogger(TransDataToCloudJob.class);


    //非controller普通類中使用@Autowired註解
    public static TransDataToCloudJob transDataToCloudJob;

    //用於在依賴關係注入完成之後需要執行的方法上，以執行任何初始化
    @PostConstruct
    public void init() {
        transDataToCloudJob = this;
    }


    // Autowired Mapper
    @Autowired
    private SHT20MST01Service sht20mst01Service;


    @SneakyThrows
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//設定日期格式
        logger.info("===> TransDataToCloudJob" + df.format(new Date()));
        

        //轉檔資料到雲端
        transDataToCloudJob.sht20mst01Service.transDataListToCloud();
    }
}
