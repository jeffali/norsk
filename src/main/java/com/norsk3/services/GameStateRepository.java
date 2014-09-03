package com.norsk3.services;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Aims to persist end result using JdbcTemplate
 * This is a work in progress. Not used yet !
 * @author jeffali
 */

@Repository
public class GameStateRepository {
    public static Log LOG = LogFactory.getLog(GameStateRepository.class);

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {        
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void insertEndResult(String tag) {
        jdbcTemplate.update("INSERT INTO RESULT(USER, CATEGORY, FAILURES, SUCCESSES) VALUES(?, ?, ?, ?)", tag, new Date());
    }

    public List<Map<String, Object>> findAllResults() {
        return jdbcTemplate.queryForList("SELECT * FROM RESULT ORDER BY TS");
    }
}
