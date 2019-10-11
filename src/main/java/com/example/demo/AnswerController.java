package com.example.demo;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/answer")
public class AnswerController {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public AnswerController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @PostMapping("/add")
    @ResponseBody
    public boolean addAnswer(@RequestBody Map<String, Object> param) {
        String sql = "insert into answer(description, queId, isRight) values(?,?,?)";
        return jdbcTemplate.update(sql, param.get("description"), param.get("queId"), param.get("isRight")) > 0;
    }

    @PostMapping("/findAnswerByQueId")
    @ResponseBody
    public Map<String, Object> findAnswerByQueId(@RequestBody Map<String, Object> param) {
        String sql = "select * from answer where queId=?";
        List<Answer> tmp;
        tmp = jdbcTemplate.query(sql, new Object[]{param.get("queId")}, new BeanPropertyRowMapper<>(Answer.class));
        Map<String, Object> tmpMP = new HashMap<>();
        if (tmp.isEmpty()) {
            tmpMP.put("status", "false");
        } else {
            tmpMP.put("status", "true");
            tmpMP.put("Answer", tmp);
        }
        return tmpMP;
    }

}
