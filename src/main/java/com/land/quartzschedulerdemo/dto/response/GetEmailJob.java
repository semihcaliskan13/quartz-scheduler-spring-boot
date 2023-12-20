package com.land.quartzschedulerdemo.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.quartz.JobDataMap;
import org.quartz.Trigger;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GetEmailJob {

    private String jobName;
    private String jobGroup;
    private JobDataMap jobData;
    List<TriggerResponse> triggersOfJob;

    public static List<TriggerResponse> buildDescriptor(List<? extends Trigger> triggers){
        List<TriggerResponse> triggerResponses = new ArrayList<>();
        for (var trigger: triggers){
            triggerResponses.add(TriggerResponse.buildJobDescriptor(trigger));
        }
        return triggerResponses;
    }

}
