package com.land.quartzschedulerdemo.dto.request;

import jakarta.validation.constraints.Email;
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
public class CreateEmailJobRequest {

    @NotEmpty
    @Email
    private String email;

    @NotEmpty
    private String subject;

    @NotEmpty
    private String body;

    @NotEmpty
    private LocalDateTime dateTime;

    @NotEmpty
    private ZoneId timeZone;
}
