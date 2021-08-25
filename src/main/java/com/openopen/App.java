package com.openopen;

import com.openopen.job.SHT20MST01Job;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

@SpringBootApplication
@ComponentScan(basePackages = {"com.openopen"})
@RestController
public class App {


    @Value("${APIENV}")
    private String APIENV;


    @RequestMapping(value = "/")
    String hello() {
        return "Hello World!" + APIENV;
    }

    @RequestMapping(value = "/env")
    String getApiEnv() {
        return APIENV;
    }


    public static void main(String[] args) throws SchedulerException {

        // ===== SpringApplication =====
        SpringApplication.run(App.class, args);


        // ===== quartz job =====
        // [0] Grab the Scheduler instance from the Factory
        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

        // and start it off
        scheduler.start();

        // [1] define the job
        JobDetail SHT20MST01Job = newJob(SHT20MST01Job.class)
                .withIdentity("SHT20MST01Job", "group1")
                .build();


        // [2] define the Trigger
        Trigger triggerSHT20MST01Job = newTrigger()
                .withIdentity("triggerSHT20MST01Job", "group1")
                .withSchedule(CronScheduleBuilder.cronSchedule("0/10 * * * * ?"))
                .forJob(SHT20MST01Job)
                .build();

        // [3] deleteJon is already exist
        if (scheduler.checkExists(SHT20MST01Job.getKey())) {
            scheduler.deleteJob(SHT20MST01Job.getKey());
        }


        // [4] define the scheduleJob
        scheduler.scheduleJob(SHT20MST01Job, triggerSHT20MST01Job);


    }
}
