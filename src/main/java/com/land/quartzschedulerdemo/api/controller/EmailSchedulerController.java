package com.land.quartzschedulerdemo.api.controller;

import com.land.quartzschedulerdemo.dto.request.CreateEmailJobRequest;
import com.land.quartzschedulerdemo.dto.request.CreateEmailJobTriggerRequest;
import com.land.quartzschedulerdemo.dto.response.CreateEmailJobResponse;
import com.land.quartzschedulerdemo.dto.response.GetEmailJob;
import com.land.quartzschedulerdemo.service.EmailJobService;
import org.quartz.SchedulerException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/schedule-jobs")
public class EmailSchedulerController {

    private final EmailJobService emailJobService;

    public EmailSchedulerController(EmailJobService emailJobService) {
        this.emailJobService = emailJobService;
    }

    @PostMapping
    public CreateEmailJobResponse scheduleEmailJob(@RequestBody CreateEmailJobRequest request) throws SchedulerException {
        return emailJobService.scheduleEmailJob(request);
    }

    @GetMapping(path = "/")
    public List<GetEmailJob> getAllEmailJobs() throws SchedulerException {
        return emailJobService.getAllEmailJobs();
    }

    @GetMapping(path = "/groups/{groupName}/jobs/{jobId}")
    public GetEmailJob getEmailJobById(@PathVariable String groupName, @PathVariable String jobId) throws SchedulerException {
        return emailJobService.getEmailJobById(jobId, groupName);
    }

    @PostMapping(path = "/triggers")
    public void addTriggersToExistingJob(@RequestBody CreateEmailJobTriggerRequest request) throws SchedulerException {
        emailJobService.addTriggersToExistingJob(request);
    }

    @GetMapping(path = "/groups/{groupName}/jobs/{jobId}/pause")
    public void pauseEmailJob(@PathVariable String groupName, @PathVariable String jobId) throws SchedulerException {
        emailJobService.pauseEmailJob(jobId, groupName);
    }

    @GetMapping(path = "/groups/{groupName}/jobs/{jobId}/resume")
    public void resumeEmailJob(@PathVariable String groupName, @PathVariable String jobId) throws SchedulerException {
        emailJobService.resumeEmailJob(jobId, groupName);
    }

    @DeleteMapping(path = "/groups/{groupName}/jobs/{jobId}")
    public void deleteEmailJob(@PathVariable String groupName, @PathVariable String jobId) throws SchedulerException {
        emailJobService.deleteJob(jobId, groupName);
    }
}
