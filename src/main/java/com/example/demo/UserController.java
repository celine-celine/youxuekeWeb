package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.*;

class userInfo {
    private int identity;
    private String name;
    private String id;

    userInfo() {
        identity = -1;
        name = "";
        id = "";
    }

    public int getIdentity() {
        return identity;
    }

    public void setIdentity(int identity) {
        this.identity = identity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}


@RestController
@RequestMapping("/user")
public class UserController {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @GetMapping("/list")
    public Map<String, Object> queryUsers() {
        String sql = "select * from user";
        List<User> tmp = jdbcTemplate.query(sql, new Object[]{}, new BeanPropertyRowMapper<>(User.class));
        Map<String, Object> tmpMP = new HashMap();
        if (tmp.isEmpty()) {
            tmpMP.put("status", "false");
        } else {
            tmpMP.put("status", "true");
            for (User u : tmp) {
                tmpMP.put("User", u);
            }
        }
        return tmpMP;
    }

    @PostMapping("/lookup")
    public Map<String, Object> findUser(@RequestBody Map<String, Object> param) {
        String sql = "select * from user where ID=?";

        Map<String, Object> response = new HashMap();
        User result = null;

        try {
            result = jdbcTemplate.queryForObject(sql, new Object[]{param.get("stuId")}, new BeanPropertyRowMapper<>(User.class));
        } catch(DataAccessException e) {
            System.out.println("Query for user " + param.get("stuId") + " failed! ");
            e.printStackTrace();
        } finally {
            if (result == null) {
                response.put("status", "false");
            } else {
                response.put("status", "true");
                response.put("User", result);
            }
        }

        return response;
    }


    @PostMapping("/register")
    @ResponseBody
    public boolean addUser(@RequestBody Map<String, Object> param) {
        WXController controller = new WXController();
        String open_id = controller.getSession((String) param.get("js_code"));
        String sql = "insert into user(ID, name, open_id,identity) values (?,?,?,1)";
        return jdbcTemplate.update(sql, param.get("stuId"), param.get("name"), open_id) > 0;
    }


    @PostMapping("/login")
    @ResponseBody
    public Map<String, Object> login(@RequestBody Map<String, Object> param) throws DataAccessException {
        String sql = "select * from user where ID =?";
        userInfo tmp = null;
        Map<String, Object> tmpMP = new HashMap<>();
        try {
            tmp = jdbcTemplate.queryForObject(sql,  new BeanPropertyRowMapper<>(userInfo.class),param.get("stuId"));
        } catch (DataAccessException e) {
            System.out.println("An unregistered user tried to login. ");
        } finally {
            if (tmp == null) {
                tmpMP.put("status", "false");
                String register = "insert into user (ID,password,name,avatar,identity) values(?,?,?,?,1)";
                jdbcTemplate.update(register, param.get("stuId"), param.get("password"),param.get("name"),param.get("avatar"));
            } else {
                tmpMP.put("status", "true");
                tmpMP.put("userInfo", tmp);
            }
        }
        return tmpMP;
    }
}
/*
    @PostMapping("/login")
    @ResponseBody
    public Map<String, Object> login(@RequestBody Map<String, Object> param) throws DataAccessException {
        WXController controller = new WXController();
        String open_id = controller.getSession((String) param.get("js_code"));
        String sql = "select identity, ID, name from user where open_id =?";
        userInfo tmp = null;
        Map<String, Object> tmpMP = new HashMap<>();
        try {
            tmp = jdbcTemplate.queryForObject(sql, new Object[]{open_id}, new BeanPropertyRowMapper<>(userInfo.class));
        } catch (DataAccessException e) {
            System.out.println("An unregistered user tried to login. ");
        } finally {

            if (tmp == null) {
                tmpMP.put("status", "false");
            } else {
                tmpMP.put("status", "true");
                tmpMP.put("userInfo", tmp);
            }
        }
        return tmpMP;
    }

//    @PostMapping("/login")
//    public userInfo login(@RequestParam("code") String js_code) {
//        WXController controller = new WXController();
//        String open_id = controller.getSession(js_code);
//        String sql ="select identity, ID, name from user where open_id =?";
//        return jdbcTemplate.queryForObject(sql, new Object[]{open_id}, new BeanPropertyRowMapper<>(userInfo.class));
//    }

    @PostMapping("/uploadAvatar")
    @ResponseBody
    public boolean uploadAvatar(@RequestBody Map<String, Object> param) throws Exception {
        PictureManager.download((String) param.get("url"), (String) param.get("stuId"), "userAvatar");
        return true;
    }

//    @PostMapping("/uploadAvatar")
//    public boolean uploadAvatar(@RequestParam("url") String url,@RequestParam("stuId") String stuId) throws Exception{
//        PictureManager.download(url,stuId,"userAvatar");
//        return true;
//    }


}
*/
