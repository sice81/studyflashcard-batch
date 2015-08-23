package com.genius.flashcard.job;

import java.io.IOException;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.PersistJobDataAfterExecution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.genius.flashcard.service.StoreInsertService;

@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class StoreInsertJob extends QuartzJobBean {
	Logger logger = LoggerFactory.getLogger(this.getClass());

	protected void executeInternal(JobExecutionContext ctx) throws JobExecutionException {
		StoreInsertService storeInsertService = (StoreInsertService) ctx.getMergedJobDataMap().get("storeInsertService");

		try {
			storeInsertService.deleteAll();
			storeInsertService.insertFromStudyActLog();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}