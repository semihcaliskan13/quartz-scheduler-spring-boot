package com.land.quartzschedulerdemo.service.impl;


import com.land.quartzschedulerdemo.dto.request.CreateEmailJobRequest;
import com.land.quartzschedulerdemo.dto.response.CreateEmailJobResponse;
import com.land.quartzschedulerdemo.dto.response.GetEmailJob;
import com.land.quartzschedulerdemo.service.ScheduleEmailJobService;
import com.land.quartzschedulerdemo.util.EmailJobUtil;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
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
        return null;
    }

    @Override
    public GetEmailJob getEmailJobById(String jobName, String groupName) throws SchedulerException {
        return null;
    }
}
