package com.itheima.dao;

import java.util.List;
import com.itheima.domain.Category;
import com.itheima.utils.C3P0Util;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

public class CategoryDao {
    private JdbcTemplate template = new JdbcTemplate(C3P0Util.getDataSource());

    public List<Category> showCategory() {
        String sql = "select * from tab_category";

        return template.query(sql, new BeanPropertyRowMapper<>(Category.class));
    }
}
