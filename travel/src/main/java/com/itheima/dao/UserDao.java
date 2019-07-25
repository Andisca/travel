package com.itheima.dao;

import com.itheima.domain.User;
import com.itheima.utils.C3P0Util;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

public class UserDao {

    private JdbcTemplate template = new JdbcTemplate(C3P0Util.getDataSource());

    public void save(User user) throws Exception {
        String sql = "insert into tab_user values (null,?,?,?,?,?,?,?,?,?)";

        Object[] params = {
                user.getUsername(),
                user.getPassword(),
                user.getName(),
                user.getBirthday(),
                user.getSex(),
                user.getTelephone(),
                user.getEmail(),
                user.getStatus(),
                user.getCode()
        };

        template.update(sql, params);
    }

    public int active(String code) {

        String sql = "update tab_user set status = ? where code = ?";

        return template.update(sql, "Y", code);
    }

    public User findUser(String username, String password) {
        String sql = "select * from tab_user where username = ? and password = ?";

        User user = null;

        try {
            user = template.queryForObject(sql, new BeanPropertyRowMapper<>(User.class), username, password);
        } catch (DataAccessException e) {
           // e.printStackTrace();
        }

        return user;
    }
}
