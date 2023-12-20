package com.land.quartzschedulerdemo.service.impl;


import com.land.quartzschedulerdemo.dto.request.CreateEmailJobRequest;
import com.land.quartzschedulerdemo.dto.request.CreateEmailJobTriggerRequest;
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

import java.time.ZoneId;
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
        scheduler.scheduleJob(jobDetail, trigger);
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
                emailJobs.add(new GetEmailJob(jobName, jobGroup, jobDataMap, GetEmailJob.buildDescriptor(triggers)));
            }

        }
        return emailJobs;
    }

    @Override
    public GetEmailJob getEmailJobById(String jobName, String groupName) throws SchedulerException {
        var jobDetail = scheduler.getJobDetail(JobKey.jobKey(jobName, groupName));
        return new GetEmailJob(jobDetail.getKey().getName(), jobDetail.getKey().getGroup(), jobDetail.getJobDataMap(), GetEmailJob.buildDescriptor(scheduler.getTriggersOfJob(JobKey.jobKey(jobName, groupName))));
    }

    @Override
    public void pauseEmailJob(String jobName, String groupName) throws SchedulerException {
        scheduler.pauseJob(JobKey.jobKey(jobName, groupName));
    }

    @Override
    public void resumeEmailJob(String jobName, String groupName) throws SchedulerException {
        scheduler.resumeJob(JobKey.jobKey(jobName, groupName));
    }

    @Override
    public void addTriggersToExistingJob(CreateEmailJobTriggerRequest request) throws SchedulerException {

        ZonedDateTime dateTime = ZonedDateTime.of(request.getDateTime(), request.getTimeZone());
        JobDetail jobDetail = scheduler.getJobDetail(JobKey.jobKey(request.getJobId(), request.getGroupName()));
        Trigger trigger = EmailJobUtil.buildTrigger(jobDetail, dateTime);
        scheduler.scheduleJob(trigger);

    }

    @Override
    public void deleteJob(String jobName, String groupName) throws SchedulerException {
        scheduler.deleteJob(JobKey.jobKey(jobName,groupName));
    }
}
