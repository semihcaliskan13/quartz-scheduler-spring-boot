package com.land.quartzschedulerdemo.quartz.job;

import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class EmailJob extends QuartzJobBean {

    @Value("${spring.mail.username}")
    private String sender;

    private final JavaMailSender javaMailSender;
    private final MailProperties mailProperties;


    public EmailJob(JavaMailSender javaMailSender, MailProperties mailProperties) {
        this.javaMailSender = javaMailSender;
        this.mailProperties = mailProperties;
    }

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        var jobDataMap = context.getMergedJobDataMap();

        String subject = jobDataMap.getString("subject");
        String body = jobDataMap.getString("body");
        String recipientEmail = jobDataMap.getString("email");

        sendSimpleEmail(subject, recipientEmail, body);
    }

    private void sendSimpleEmail(String subject, String recipientEmail, String body) {
        var mail = new SimpleMailMessage();
        mail.setFrom(mailProperties.getUsername());
        mail.setSubject(subject);
        mail.setText(body);
        mail.setTo(recipientEmail);
        javaMailSender.send(mail);

    }
}
