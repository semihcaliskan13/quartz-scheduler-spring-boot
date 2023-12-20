package com.land.quartzschedulerdemo.util;

import com.land.quartzschedulerdemo.dto.request.CreateEmailJobRequest;
import com.land.quartzschedulerdemo.quartz.job.EmailJob;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;

import java.sql.Date;
import java.time.ZonedDateTime;
import java.util.UUID;

public class EmailJobUtil {

    public static JobDetail buildJobDetail(CreateEmailJobRequest emailJobRequest) {
        var jobDataMap = new JobDataMap();
        jobDataMap.put("email", emailJobRequest.getEmail());
        jobDataMap.put("subject", emailJobRequest.getSubject());
        jobDataMap.put("body", emailJobRequest.getBody());

        return JobBuilder
                .newJob(EmailJob.class).
                withIdentity(UUID.randomUUID().toString(), "email-jobs")
                .withDescription("send-email-job")
                .usingJobData(jobDataMap)
                .storeDurably()
                .build();
    }

    public static Trigger buildTrigger(JobDetail jobDetail, ZonedDateTime startAt) {
        return TriggerBuilder.newTrigger().
                forJob(jobDetail)
                .withIdentity(UUID.randomUUID().toString(), "email-triggers")
                .withDescription("send-email-trigger")
                .startAt(Date.from(startAt.toInstant()))
                .withSchedule(SimpleScheduleBuilder.simpleSchedule().withMisfireHandlingInstructionFireNow())
                .build();

    }

}
