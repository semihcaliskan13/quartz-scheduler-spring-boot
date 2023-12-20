package com.land.quartzschedulerdemo.service;

import com.land.quartzschedulerdemo.dto.request.CreateEmailJobRequest;
import com.land.quartzschedulerdemo.dto.response.CreateEmailJobResponse;
import com.land.quartzschedulerdemo.dto.response.GetEmailJob;
import org.quartz.SchedulerException;

import java.util.List;

public interface ScheduleEmailJobService {

    CreateEmailJobResponse scheduleEmailJob(CreateEmailJobRequest request) throws SchedulerException;
    List<GetEmailJob> getAllEmailJobs() throws SchedulerException;
    GetEmailJob getEmailJobById(String jobName, String groupName) throws SchedulerException;

}