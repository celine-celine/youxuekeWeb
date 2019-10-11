package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/booking")
public class BookingController {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BookingController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @PostMapping("/list")
    public Map<String, Object> queryBookings() {
        String sql = "select * from booking";
        List<Booking> tmp;
        tmp = jdbcTemplate.query(sql, new Object[]{}, new BeanPropertyRowMapper<>(Booking.class));
        Map<String, Object> tmpMP = new HashMap<>();
        if (tmp.isEmpty()) {
            tmpMP.put("status", "false");
        } else {
            tmpMP.put("status", "true");
            tmpMP.put("Booking", tmp);
        }
        return tmpMP;
    }

    @PostMapping("/findCourseByStu")
    @ResponseBody
    public Map<String, Object> findCourseByStu(@RequestBody Map<String, Object> param) {
        String sql = "select * from course, booking where course.id = booking.courseId and booking.stuId = ?";
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
    /*public List<Course> findCourseByStu(@RequestParam("stuId") String stuId) {
        String sql = "select * from course, booking where course.id = booking.courseId and booking.stuId = ?";
        return jdbcTemplate.query(sql,new Object[]{stuId}, new BeanPropertyRowMapper<>(Course.class));
    }*/

    @PostMapping("/add")
    @ResponseBody
    public boolean addBooking(@RequestBody Map<String, Object> param) {
        String sql = "insert into booking(stuId, courseId, phone) values(?,?,?)";
        return jdbcTemplate.update(sql, param.get("stuId"), param.get("courseId"), param.get("phone")) > 0;
    }

//    @PostMapping("/add")
//    public boolean addBooking(@RequestParam("stuId") String stuId, @RequestParam("courseId") int courseId, @RequestParam("phone") String phone) {
//        String sql = "insert into booking(stuId, courseId, phone) values(?,?,?)";
//        return jdbcTemplate.update(sql, stuId, courseId, phone) > 0;
//    }
}
