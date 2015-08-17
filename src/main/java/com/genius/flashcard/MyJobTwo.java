package com.genius.flashcard;

import org.springframework.stereotype.Service;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.quartz.PersistJobDataAfterExecution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class MyJobTwo extends QuartzJobBean {
	Logger logger = LoggerFactory.getLogger(this.getClass());

	public static final String COUNT = "count";
	private String name;

	protected void executeInternal(JobExecutionContext ctx) throws JobExecutionException {
		JobDataMap dataMap = ctx.getJobDetail().getJobDataMap();
		int cnt = dataMap.getInt(COUNT);
		JobKey jobKey = ctx.getJobDetail().getKey();
		logger.info(jobKey + ": " + name + ": " + cnt);
		cnt++;
		dataMap.put(COUNT, cnt);
	}

	public void setName(String name) {
		this.name = name;
	}
}