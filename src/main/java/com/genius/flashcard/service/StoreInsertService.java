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

		sb.append("INSERT INTO CARDPACK_STORE \n" );
		sb.append("WITH WT1 AS ( \n" );
		sb.append("  SELECT B.CARDPACK_ID, NVL(SUM(A.BACK_VIEW_CNT) + SUM(A.RIGHT_CNT)*2 + SUM(A.WRONG_CNT), 0) IDX FROM CARDPACKS B, STUDY_ACT_LOG A \n" );
		sb.append("  WHERE B.CARDPACK_ID = A.CARDPACK_ID(+) \n" );
		sb.append("  GROUP BY B.CARDPACK_ID \n" );
		sb.append("), WT2 AS ( \n" );
		sb.append("  SELECT ROW_NUMBER() OVER (ORDER BY A.IDX DESC) RANK, A.IDX, A.CARDPACK_ID, B.CARDPACK_NAME TITLE FROM WT1 A, CARDPACKS B \n" );
		sb.append("  WHERE A.CARDPACK_ID = B.CARDPACK_ID \n" );
		sb.append(") \n" );
		sb.append("SELECT RANK, CARDPACK_ID, TITLE FROM WT2");

		jdbcTemplate.update(sb.toString());
	}
}