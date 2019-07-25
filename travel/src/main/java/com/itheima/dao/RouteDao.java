package com.itheima.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.itheima.domain.Route;
import com.itheima.domain.RouteImg;
import com.itheima.utils.C3P0Util;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

public class RouteDao {
    private JdbcTemplate template = new JdbcTemplate(C3P0Util.getDataSource());

    public List<Route> findPopular() {
        String sql = "SELECT * FROM tab_route WHERE rflag = '1' ORDER BY COUNT DESC LIMIT 0,4";

        return template.query(sql, new BeanPropertyRowMapper<>(Route.class));
    }

    public List<Route> findNewest() {
        String sql = "SELECT * FROM tab_route WHERE rflag = '1' ORDER BY rdate DESC LIMIT 0,4";

        return template.query(sql, new BeanPropertyRowMapper<>(Route.class));
    }

    public List<Route> findTheme() {
        String sql = "SELECT * FROM tab_route WHERE rflag = '1' AND isThemeTour = '1' LIMIT 0,4";

        return template.query(sql, new BeanPropertyRowMapper<>(Route.class));
    }

    public Integer findCount(String cid, String rname) {
        String sql = "SELECT COUNT(*) FROM tab_route WHERE rflag = '1'";

        ArrayList<Object> params = new ArrayList<>();

        if (null != cid && !"".equals(cid) && !"null".equals(cid)) {
            sql += " AND cid = ?";
            params.add(cid);
        }

        if (null != rname && !"".equals(rname)) {
            sql += " AND rname LIKE ?";
            params.add("%" + rname + "%");
        }

        return template.queryForObject(sql, Integer.class, params.toArray());
    }

    public List<Route> findList(String cid, int skipCount, Integer pageSize, String rname) {
        String sql = "SELECT * FROM tab_route WHERE rflag = '1'";

        ArrayList<Object> params = new ArrayList<>();

        if (null != cid && !"".equals(cid) && !"null".equals(cid)) {
            sql += " AND cid = ?";
            params.add(cid);
        }

        if (null != rname && !"".equals(rname)) {
            sql += " AND rname LIKE ?";
            params.add("%" + rname + "%");
        }

        sql += " LIMIT ?, ?";

        params.add(skipCount);
        params.add(pageSize);

        return template.query(sql, new BeanPropertyRowMapper<>(Route.class), params.toArray());
    }

    public Map<String, Object> findByRid(String rid) {
        String sql = "SELECT * FROM tab_route r, tab_category c, tab_seller s WHERE r.cid = c.cid AND r.sid = s.sid AND r.rid = ?";

        return template.queryForMap(sql, rid);
    }

    public List<RouteImg> findRouteImg(String rid) {
        String sql = "SELECT * FROM tab_route_img WHERE rid = ?";

        return template.query(sql, new BeanPropertyRowMapper<>(RouteImg.class), rid);
    }
}
