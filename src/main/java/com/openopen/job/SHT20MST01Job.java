package com.openopen.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SHT20MST01Job implements Job {

    //Logger
    private static final Logger logger = LoggerFactory.getLogger(SHT20MST01Job.class);


    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//設定日期格式
//        System.out.println(df.format(new Date()));// new Date()為獲取當前系統時間
//        System.out.println("MyJob Quartz!!!");


        logger.info("===> SHT20MST01Job" + df.format(new Date()));


    }

}
