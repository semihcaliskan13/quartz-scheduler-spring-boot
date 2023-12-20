package com.land.quartzschedulerdemo.api.controller;

import com.land.quartzschedulerdemo.dto.request.CreateEmailJobRequest;
import com.land.quartzschedulerdemo.dto.response.CreateEmailJobResponse;
import com.land.quartzschedulerdemo.service.ScheduleEmailJobService;
import org.quartz.SchedulerException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class EmailSchedulerController {

    private final ScheduleEmailJobService emailJobService;

    public EmailSchedulerController(ScheduleEmailJobService emailJobService) {
        this.emailJobService = emailJobService;
    }

    @PostMapping
    public CreateEmailJobResponse scheduleEmailJob(@RequestBody CreateEmailJobRequest request) throws SchedulerException {
        return emailJobService.scheduleEmailJob(request);
    }
}
