package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.*;

class Topic_Liker {
    private String topicId;
    private String likerId;

    public String getTopicId() {
        return topicId;
    }

    public void setTopicId(String topicId) {
        this.topicId = topicId;
    }

    public String getLikerId() {
        return likerId;
    }

    public void setLikerId(String likerId) {
        this.likerId = likerId;
    }
}

class Entry {
    private int key;
    private String value;

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "key: " + this.key + " value: " + this.value;
    }
}


@RestController
@RequestMapping("/topic")
public class TopicController {
    List<Entry> list = new ArrayList<>();
    private final JdbcTemplate jdbcTemplate;
    static int count = 0;
    static boolean flag = false;

    @Autowired
    public TopicController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @PostMapping("/list")
    public Map<String, Object> queryTopics() {
        String sql = "select * from topic";
        List<Topic> tmp;
        tmp = jdbcTemplate.query(sql, new Object[]{}, new BeanPropertyRowMapper<>(Topic.class));
        Map<String, Object> tmpMP = new HashMap<>();
        if (tmp.isEmpty()) {
            tmpMP.put("status", "false");
        } else {
            tmpMP.put("status", "true");
            tmpMP.put("Topic", tmp);
        }
        return tmpMP;
    }

    @PostMapping("/findTopicById")
    @ResponseBody
    public Map<String, Object> findTopicById(@RequestBody Map<String, Object> param) {
        String sql = "select * from topic where id=?";
        Topic tmp = null;
        Map<String, Object> tmpMP = new HashMap<>();
        try {
            tmp = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Topic.class), param.get("topicId"));
        } catch (DataAccessException e) {
            System.out.println("No such topic. ");
        } finally {
            if (tmp == null) {
                tmpMP.put("status", "false");
            } else {
                tmpMP.put("status", "true");
                tmpMP.put("Topic", tmp);
            }
        }
        return tmpMP;
    }


    @PostMapping("/findTopicByAuthor")
    @ResponseBody
    public Map<String, Object> findTopicByAuthor(@RequestBody Map<String, Object> param) {
        String sql = "select * from tipic where author = ?";
        List<Topic> tmp;
        tmp = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Topic.class), param.get("stuId"));
        Map<String, Object> tmpMP = new HashMap<>();
        if (tmp.isEmpty()) {
            tmpMP.put("status", "false");
        } else {
            tmpMP.put("status", "true");
            tmpMP.put("Topic", tmp);
        }
        return tmpMP;
    }

//    @PostMapping("/findTopicByAuthor")
//    public Topic findTopicByAuthor(@RequestParam("stuId") String stuId) {
//        String sql = "select * from tipic where author = ?";
//        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Topic.class), stuId);
//    }

    @PostMapping("/findRepliesByTopic")
    @ResponseBody
    public Map<String, Object> findRepliesByTopic(@RequestBody Map<String, Object> param) {
        String sql = "select * from reply where topic_id=?";
        List<Reply> tmp;
        tmp = jdbcTemplate.query(sql, new Object[]{param.get("topicId")}, new BeanPropertyRowMapper<>(Reply.class));
        Map<String, Object> tmpMP = new HashMap<>();
        if (tmp.isEmpty()) {
            tmpMP.put("status", "false");
        } else {
            tmpMP.put("status", "true");
            tmpMP.put("Reply", tmp);
        }
        return tmpMP;
    }

//    @PostMapping("/findRepliesByTopic")
//    public List<Reply> findRepliesByTopic(@RequestParam("topic_id") int topic_id) {
//        String sql = "select * from reply where topic_id=?";
//        return jdbcTemplate.query(sql, new Object[]{topic_id}, new BeanPropertyRowMapper<>(Reply.class));
//    }

    @PostMapping("/add")
    @ResponseBody
    public synchronized Map<String, Object> addTopic(@RequestBody Map<String, Object> param) {
        String selectMax = "select max(id) from topic";
        String sql = "insert into topic(title, des, author, date, likes, replies) values(?,?,?,?,0,0)";
        boolean success = jdbcTemplate.update(
                sql,
                param.get("title"),
                param.get("des"),
                param.get("stuId"),
                param.get("date")
        ) > 0;
        Map<String, Object> tmpMP = new HashMap<>();
        if (success) {
            tmpMP.put("status", "true");
            tmpMP.put("topicID", jdbcTemplate.queryForObject(selectMax, Integer.class));
        } else {
            tmpMP.put("status", "false");
        }
        return tmpMP;
    }

    @PostMapping("/addLikes")
    @ResponseBody
    public boolean addLikesToTopic(@RequestBody Map<String, Object> param) {
        String sql = "select * from topic_liker where topic=? and liker=?";
        if (!jdbcTemplate.query(sql, new Object[]{param.get("topicId"), param.get("stuId")}, new BeanPropertyRowMapper<>(Topic_Liker.class)).isEmpty()) {
            return false;
        } else {
            String sql1 = "insert into topic_liker(topic, liker) values(?,?)";
            jdbcTemplate.update(sql1, param.get("topicId"), param.get("stuId"));
            String sql2 = "update topic set likes=likes+1 where id=?";
            jdbcTemplate.update(sql2, param.get("topicId"));
            return true;
        }
    }


    @PostMapping("/getSumLikes")
    @ResponseBody
    public Map<String, Object> getSumLikes(@RequestBody Map<String, Object> param) {
        String sql = "select count(*) from topic_liker where topic=? ";
        Integer tmp = null;
        Map<String, Object> tmpMP = new HashMap<>();
        try {
            tmp = jdbcTemplate.queryForObject(sql, new Object[]{param.get("topicId")}, Integer.class);
        } catch (DataAccessException e) {
            System.out.println("Failure, cannot find this topic_id ");
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


    @PostMapping("/getSumReplies")
    @ResponseBody
    public Map<String, Object> getSumReplies(@RequestBody Map<String, Object> param) {
        String sql = "select count(*) from reply where topic_id=?";
        Integer tmp = null;
        Map<String, Object> tmpMP = new HashMap<>();
        try {
            tmp = jdbcTemplate.queryForObject(sql, new Object[]{param.get("topicId")}, Integer.class);
        } catch (DataAccessException e) {
            System.out.println("Failure, cannot find this topic_id ");
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


    /*Pay additional attention !!!!!!!!!!!!!!!!!!!!!!!!!!
     * here !!!!!!!!!!!!topic id is transferred as object, not sure if it's ok to directly transfer to string*/
    @PostMapping("/uploadPic")
    @ResponseBody
    public boolean uploadPic(@RequestBody Map<String, Object> param) throws Exception {
        PictureManager.download((String) param.get("url"), (String) param.get("topicId"), "topicPic");
        return true;
    }


}
