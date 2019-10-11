package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/course")
public class CourseController {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public CourseController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @PostMapping("/list")
    public Map<String, Object> queryCourses() {
        String sql = "select * from course";
        List<Course> tmp;
        tmp = jdbcTemplate.query(sql, new Object[]{}, new BeanPropertyRowMapper<>(Course.class));
        Map<String, Object> tmpMP = new HashMap<>();
        if (tmp.isEmpty()) {
            tmpMP.put("status", "false");
        } else {
            tmpMP.put("status", "true");
            tmpMP.put("Course", tmp);
        }
        return tmpMP;
    }

    @PostMapping("/findCourseById")
    @ResponseBody
    public Map<String, Object> findCourseById(@RequestBody Map<String, Object> param) {
        String sql = "select * from course where id=?";
        Course tmp = null;
        Map<String, Object> tmpMP = new HashMap<>();
        try {
            tmp = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Course.class), param.get("courseId"));
        } catch (DataAccessException e) {
            System.out.println("No such course. ");
        } finally {
            if (tmp == null) {
                tmpMP.put("status", "false");
            } else {
                tmpMP.put("status", "true");
                tmpMP.put("Course", tmp);
            }
        }
        return tmpMP;
    }

//    @PostMapping("/findCourseById")
//    public Course findCourseById(@RequestParam("id") int id) {
//        String sql = "select * from course where id=?";
//        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Course.class), id);
//    }

    @PostMapping("/findCourseByDate")
    @ResponseBody
    public Map<String, Object> findCourseByDate(@RequestBody Map<String, Object> param) {
        String sql = "select * from course where date like ?";
        List<Course> tmp;
        
        // check the format of the date
        String date = param.get("date").toString();
        String[] piece = date.split("-");
        if(piece[1].length() == 1) {
            piece[1] = "0" + piece[1];
        }
        if(piece[2].length() == 1) {
            piece[2] = "0" + piece[2];
        }
        date = piece[0] + "-" + piece[1] + "-" + piece[2];

        tmp = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Course.class), date + "%");
        Map<String, Object> tmpMP = new HashMap<>();
        if (tmp.isEmpty()) {
            tmpMP.put("status", "false");
        } else {
            tmpMP.put("status", "true");
            tmpMP.put("Course", tmp);
        }
        return tmpMP;
    }

//    @PostMapping("/findCourseByDate")
//    public List<Course> findCourseByDate(@RequestParam("date") String date) {
//        String sql = "select * from course where date=?";
//        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Course.class), date);
//    }

    @PostMapping("/findCourseByTitle")
    @ResponseBody
    public Map<String, Object> findCourseByTitle(@RequestBody Map<String, Object> param) {
        String sql = "select * from course where title=?";
        List<Course> tmp;
        tmp = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Course.class), param.get("title"));
        Map<String, Object> tmpMP = new HashMap<>();
        if (tmp.isEmpty()) {
            tmpMP.put("status", "false");
        } else {
            tmpMP.put("status", "true");
            tmpMP.put("Course", tmp);
        }
        return tmpMP;
    }

//    @PostMapping("/findCourseByTitle")
//    public List<Course> findCourseByTitle(@RequestParam("title") String title) {
//        String sql = "select * from course where title=?";
//        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Course.class), title);
//    }

    @PostMapping("/add")
    @ResponseBody
    public synchronized Map<String, Object> addCourse(@RequestBody Map<String, Object> param) {
        String selectMax = "select max(id) from course";
        String sql = "insert into course(title, des, lecturer, date, location, category, trueName, phone, verification) values(?,?,?,?,?,?,?,?,0)";
        boolean success = jdbcTemplate.update(
            sql, 
            param.get("title"), 
            param.get("des"),
            param.get("stuId"), 
            param.get("date"), 
            param.get("location"), 
            param.get("category"), 
            param.get("trueName"), 
            param.get("phone")
        ) > 0;
        Map<String, Object> tmpMP = new HashMap<>();
        if (success) {
            tmpMP.put("status", "true");
            tmpMP.put("courseID", jdbcTemplate.queryForObject(selectMax, Integer.class));
        } else {
            tmpMP.put("status", "false");
        }
        return tmpMP;
    }

    @PostMapping("/verify")
    @ResponseBody
    public boolean verifyCourse(@RequestBody Map<String, Object> param) {
        String sql = "update course set verification=1 where id=?";
        return jdbcTemplate.update(sql, param.get("courseId")) > 0;
    }

    @PostMapping("/changeLocation")
    @ResponseBody
    public boolean changeLoc(@RequestBody Map<String, Object> param) {
        String sql = "update course set location=? where id=?";
        return jdbcTemplate.update(sql, param.get("location"), param.get("courseId")) > 0;
    }

    @PostMapping("/changeDate")
    @ResponseBody
    public boolean changeDate(@RequestBody Map<String, Object> param) {
        String sql = "update course set date=? where id=?";
        return jdbcTemplate.update(sql, param.get("date"), param.get("courseId")) > 0;
    }


//    @PostMapping("/add")
//    public synchronized int addCourse(@RequestParam("title") String title, @RequestParam("des") String des,@RequestParam("stuId")String lecturer, @RequestParam("date") String date, @RequestParam("location") String location) {
//        String selectMax = "select max(id) from course";
//        String sql = "insert into course(title, des, lecturer, date, location) values(?,?,?,?,?)";
//        boolean success = jdbcTemplate.update(sql, title, des, lecturer, date, location) > 0;
//        return jdbcTemplate.queryForObject(selectMax,Integer.class);
//    }

    @PostMapping("/uploadPic")
    @ResponseBody
    public boolean uploadPic(@RequestBody Map<String, Object> param) throws Exception {
        PictureManager.download((String) param.get("url"), (String) param.get("courseId"), "coursePic");
        return true;
    }


}
