package com.land.quartzschedulerdemo.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.quartz.Trigger;

import java.time.LocalDateTime;
import java.time.ZoneId;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class TriggerResponse {

    private String name;
    private String group;
    private LocalDateTime fireTime;
    private String cronTime;

    public static TriggerResponse buildJobDescriptor(Trigger trigger) {
        TriggerResponse triggerResponse = new TriggerResponse();
        triggerResponse.setName(trigger.getKey().getName());
        triggerResponse.setGroup(trigger.getKey().getGroup());
        triggerResponse.setFireTime(trigger.getNextFireTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
        triggerResponse.setCronTime(trigger.getJobDataMap().getString("cron"));

        return triggerResponse;
    }
}
