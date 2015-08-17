package com.genius.flashcard.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class StoreInsertService {
	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	JdbcTemplate jdbcTemplate;

	public void deleteAll() {
		logger.info("deleteAll()");
		jdbcTemplate.execute("DELETE FROM CARDPACK_STORE");
	}

	public void insertFromStudyActLog() {
		logger.info("insertFrom()");
		StringBuffer sb = new StringBuffer();

		sb.append("INSERT INTO CARDPACK_STORE                                                                                            " );
		sb.append("WITH WT1 AS (                                                                                                         " );
		sb.append("  SELECT CARDPACK_ID, SUM(BACK_VIEW_CNT) + SUM(RIGHT_CNT)*2 + SUM(WRONG_CNT) IDX FROM STUDY_ACT_LOG                 " );
		sb.append("  GROUP BY CARDPACK_ID                                                                                                " );
		sb.append("  ORDER BY 2 DESC                                                                                                     " );
		sb.append("), WT2 AS (                                                                                                           " );
		sb.append("  SELECT RANK() OVER (ORDER BY A.IDX DESC) RANK, A.IDX, A.CARDPACK_ID, B.CARDPACK_NAME TITLE FROM WT1 A, CARDPACKS B" );
		sb.append("  WHERE A.CARDPACK_ID = B.CARDPACK_ID                                                                                 " );
		sb.append(")                                                                                                                     " );
		sb.append("SELECT RANK, CARDPACK_ID, TITLE FROM WT2                                                                               ");

		jdbcTemplate.update(sb.toString());
	}
}