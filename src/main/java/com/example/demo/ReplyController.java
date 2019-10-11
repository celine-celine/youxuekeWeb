package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

class Reply_Liker {
    private String replyId;
    private String likerId;

    public String getLikerId() {
        return likerId;
    }

    public void setLikerId(String likerId) {
        this.likerId = likerId;
    }

    public String getReplyId() {
        return replyId;
    }

    public void setReplyId(String replyId) {
        this.replyId = replyId;
    }
}

@RestController
@RequestMapping("/reply")
public class ReplyController {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ReplyController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @PostMapping("/add")
    @ResponseBody
    public synchronized Map<String, Object> addReply(@RequestBody Map<String, Object> param) {
        String selectMax = "select max(id) from reply";
        String sql = "insert into reply(topic_id, des, author, type, date, oldCommenter) values(?,?,?,?,?,0)";
        boolean success = jdbcTemplate.update(
            sql, 
            param.get("topicId"), 
            param.get("des"), 
            param.get("stuId"),
            param.get("type"),
            param.get("oldCommenter")
        ) > 0;
        String sql1 = "update topic set replies=replies+1 where id=?";
        jdbcTemplate.update(sql1, param.get("topicId"));
        Map<String, Object> tmpMP = new HashMap<>();
        if (success) {
            tmpMP.put("status", "true");
            tmpMP.put("replyID", jdbcTemplate.queryForObject(selectMax, Integer.class));
        } else {
            tmpMP.put("status", "false");
        }
        return tmpMP;

    }

//    @PostMapping("/add")
//    public synchronized int addReply(@RequestParam("topic_id") int topic_id, @RequestParam("des") String des, @RequestParam("stuId") String author) {
//        String selectMax = "select max(id) from reply";
//        String sql = "insert into reply(topic_id, des, author, likes) values(?,?,?,0)";
//        boolean success = jdbcTemplate.update(sql, topic_id, des, author) > 0;
//        String sql1 = "update topic set replies=replies+1 where id=?";
//        jdbcTemplate.update(sql1,topic_id);
//        return jdbcTemplate.queryForObject(selectMax,Integer.class);
//    }

    @PostMapping("/addLikes")
    @ResponseBody
    public boolean addLikesToReply(@RequestBody Map<String, Object> param) {
        String sql = "select * from reply_liker where reply=? and liker=?";
        if (!jdbcTemplate.query(sql, new Object[]{param.get("replyId"), param.get("likerId")}, new BeanPropertyRowMapper<>(Reply_Liker.class)).isEmpty()) {
            return false;
        } else {
            String sql1 = "insert into reply_liker(reply, liker) values(?,?)";
            jdbcTemplate.update(sql1, param.get("replyId"), param.get("likerId"));
            String sql2 = "update reply set likes=likes+1 where id=?";
            jdbcTemplate.update(sql2, param.get("replyId"));
            return true;
        }
    }

//    @PostMapping("/addLikes")
//    public boolean addLikesToReply(@RequestParam("liker") String likerId, @RequestParam("reply_id") int replyId){
//        String sql = "select * from reply_liker where reply=? and liker=?";
//        if (!jdbcTemplate.query(sql,new Object[]{replyId,likerId},new BeanPropertyRowMapper<>(Reply_Liker.class)).isEmpty()){
//            return false;
//        }else{
//            String sql1 = "insert into reply_liker(reply, liker) values(?,?)";
//            jdbcTemplate.update(sql1, replyId, likerId);
//            String sql2 = "update reply set likes=likes+1 where id=?";
//            jdbcTemplate.update(sql2,replyId);
//            return true;
//        }
//    }

    @PostMapping("/getSumLikes")
    @ResponseBody
    public Map<String, Object> getSumLikes(@RequestBody Map<String, Object> param) {
        String sql = "select count(*) from reply_liker where reply=?";
        Integer tmp = null;
        Map<String, Object> tmpMP = new HashMap<>();
        try {
            tmp = jdbcTemplate.queryForObject(sql, new Object[]{param.get("replyId")}, Integer.class);
        } catch (DataAccessException e) {
            System.out.println("Failure, cannot find this reply_id ");
        } finally {
            if (tmp == null) {
                tmpMP.put("status", "false");
            } else {
                tmpMP.put("status", "true");
                tmpMP.put("Sum", tmp);
            }
        }
        return tmpMP;
    }

//    @PostMapping("/getSumLikes")
//    public int getSumLikes(@RequestParam("reply_id") int replyId){
//        String sql = "select count(*) from reply_liker where reply=?";
//        return jdbcTemplate.queryForObject(sql,new Object[]{replyId},Integer.class);
//    }
}
