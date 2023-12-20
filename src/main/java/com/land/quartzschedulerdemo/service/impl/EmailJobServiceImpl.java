package com.land.quartzschedulerdemo.service.impl;


import com.land.quartzschedulerdemo.dto.request.CreateEmailJobRequest;
import com.land.quartzschedulerdemo.dto.response.CreateEmailJobResponse;
import com.land.quartzschedulerdemo.dto.response.GetEmailJob;
import com.land.quartzschedulerdemo.dto.response.TriggerResponse;
import com.land.quartzschedulerdemo.service.ScheduleEmailJobService;
import com.land.quartzschedulerdemo.util.EmailJobUtil;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class EmailJobServiceImpl implements ScheduleEmailJobService {

    private final Scheduler scheduler;

    public EmailJobServiceImpl(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    @Override
    public CreateEmailJobResponse scheduleEmailJob(CreateEmailJobRequest request) throws SchedulerException {

        ZonedDateTime dateTime = ZonedDateTime.of(request.getDateTime(), request.getTimeZone());
        if (dateTime.isBefore(ZonedDateTime.now())) {
            return new CreateEmailJobResponse(false, null, null, "Past date!");
        }
        JobDetail jobDetail = EmailJobUtil.buildJobDetail(request);
        Trigger trigger = EmailJobUtil.buildTrigger(jobDetail, dateTime);
        scheduler.scheduleJob(jobDetail,trigger);

        /*
         * to add existing job a trigger we can use this code line
         *  get the existing job
         *  JobDetail jobDetail = scheduler.getJobDetail(JobKey.jobKey("job-id","group-name"));
         *  Trigger trigger = EmailJobUtil.buildTrigger(jobDetail, dateTime);
         *  scheduler.scheduleJob(trigger);
         */
        return new CreateEmailJobResponse(true, jobDetail.getKey().getName(), jobDetail.getKey().getGroup(), "E-mail job successfully scheduled.");
    }

    @Override
    public List<GetEmailJob> getAllEmailJobs() throws SchedulerException {
        var groupNames = scheduler.getJobGroupNames();
        var emailJobs = new ArrayList<GetEmailJob>();

        for (String groupName : groupNames
        ) {
            for (var jobKey : scheduler.getJobKeys(GroupMatcher.jobGroupEquals(groupName))) {
                String jobName = jobKey.getName();
                String jobGroup = jobKey.getGroup();
                var jobDataMap = scheduler.getJobDetail(jobKey).getJobDataMap();

                //get job's triggers
                var triggers = (List<? extends Trigger>) scheduler.getTriggersOfJob(jobKey);
                Date nextFireTime = triggers.get(0).getNextFireTime();

                emailJobs.add(new GetEmailJob(jobName, jobGroup, jobDataMap, GetEmailJob.buildDescriptor(triggers)));
            }

        }
        return emailJobs;
    }

    @Override
    public GetEmailJob getEmailJobById(String jobName, String groupName) throws SchedulerException {
        return null;
    }
}
