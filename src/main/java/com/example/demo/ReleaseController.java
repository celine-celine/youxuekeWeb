package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/release")
public class ReleaseController {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ReleaseController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @PostMapping("/list")
    public Map<String, Object> queryReleases() {
        String sql = "select * from re1ease";
        List<Release> tmp;
        tmp = jdbcTemplate.query(sql, new Object[]{}, new BeanPropertyRowMapper<>(Release.class));
        Map<String, Object> tmpMP = new HashMap<>();
        if (tmp.isEmpty()) {
            tmpMP.put("status", "false");
        } else {
            tmpMP.put("status", "true");
            tmpMP.put("Release", tmp);
        }
        return tmpMP;
    }

    @PostMapping("/findCourseByTea")
    @ResponseBody
    public Map<String, Object> findCourseByTea(@RequestBody Map<String, Object> param) {
        String sql = "select * from course, re1ease where course.id = re1ease.courseId and re1ease.teaId = ?";
        List<Course> tmp;
        Map<String, Object> tmpMP = new HashMap<>();
        tmp = jdbcTemplate.query(sql, new Object[]{param.get("stuId")}, new BeanPropertyRowMapper<>(Course.class));
        if (tmp.isEmpty()) {
            tmpMP.put("status", "false");
        } else {
            tmpMP.put("status", "true");
            tmpMP.put("Course", tmp);

        }
        return tmpMP;
    }

//    @PostMapping("/findCourseByTea")
//    public List<Course> findCourseByStu(@RequestParam("teaId") String teaId) {
//        String sql = "select * from course, re1ease where course.id = re1ease.courseId and re1ease.teaId = ?";
//        return jdbcTemplate.query(sql,new Object[]{teaId}, new BeanPropertyRowMapper<>(Course.class));
//    }

    @PostMapping("/add")
    @ResponseBody
    public boolean addRelease(@RequestBody Map<String, Object> param) {
        String sql = "insert into re1ease(teaId, courseId, phone,trueName) values(?,?,?,?)";
        return jdbcTemplate.update(sql, param.get("stuId"), param.get("courseId"), param.get("phone"), param.get("trueName")) > 0;
    }

//    @PostMapping("/add")
//    public boolean addRelease(@RequestParam("teaId") String teaId, @RequestParam("courseId") int courseId, @RequestParam("phone") String phone) {
//        String sql = "insert into re1ease(teaId, courseId, phone) values(?,?,?)";
//        return jdbcTemplate.update(sql, teaId, courseId, phone) > 0;
//    }
}
