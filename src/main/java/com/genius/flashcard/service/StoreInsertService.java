package com.genius.flashcard.service;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.genius.flashcard.utils.ResourceReadUtil;

@Service
public class StoreInsertService {
	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	JdbcTemplate jdbcTemplate;

	public void deleteAll() {
		logger.info("deleteAll()");
		jdbcTemplate.execute("DELETE FROM CARDPACK_STORE");
	}

	public void insertFromStudyActLog() throws IOException {
		logger.info("insertFrom()");

		String sql = ResourceReadUtil.getResource("/sql/insertFromStudyActLog.sql");
		jdbcTemplate.update(sql);
	}
}