package com.land.quartzschedulerdemo.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.ZoneId;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CreateEmailJobTriggerRequest {

    private String groupName;
    private String jobId;

    @NotEmpty
    private LocalDateTime dateTime;
    @NotEmpty
    private ZoneId timeZone;
}
