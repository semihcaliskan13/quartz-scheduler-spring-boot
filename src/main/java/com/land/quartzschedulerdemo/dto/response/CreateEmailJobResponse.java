package com.land.quartzschedulerdemo.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CreateEmailJobResponse {

    private boolean success;
    private String jobId;
    private String jobGroup;
    private String message;
}
