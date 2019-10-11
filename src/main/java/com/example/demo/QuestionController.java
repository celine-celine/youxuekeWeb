package com.example.demo;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/question")
public class QuestionController {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public QuestionController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @PostMapping("/add")
    @ResponseBody
    public synchronized Map<String, Object> addQuestion(@RequestBody Map<String, Object> param) {
        String selectMax = "select max(id) from question";
        String sql = "insert into question(description) values(?)";
        boolean success = jdbcTemplate.update(sql, param.get("description")) > 0;
        Map<String, Object> tmpMP = new HashMap<>();
        if (success) {
            tmpMP.put("status", "true");
            tmpMP.put("questionID", jdbcTemplate.queryForObject(selectMax, Integer.class));
        } else {
            tmpMP.put("status", "false");
        }
        return tmpMP;
    }

    @PostMapping("/findQuestionById")
    @ResponseBody
    public Map<String, Object> findQuestionById(@RequestBody Map<String, Object> param) {
        String sql = "select * from question where id=?";
        Question tmp = null;
        Map<String, Object> tmpMP = new HashMap<>();
        try {
            tmp = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Question.class), param.get("questionId"));
        } catch (DataAccessException e) {
            System.out.println("No such question. ");
        } finally {
            if (tmp == null) {
                tmpMP.put("status", "false");
            } else {
                tmpMP.put("status", "true");
                tmpMP.put("Question", tmp);
            }
        }
        return tmpMP;
    }
}
