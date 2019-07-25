package com.itheima.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import com.itheima.domain.Favorite;
import com.itheima.domain.Route;
import com.itheima.utils.C3P0Util;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

public class FavoriteDao {
    private JdbcTemplate template = new JdbcTemplate(C3P0Util.getDataSource());

    public Favorite findFavorited(Integer rid, int uid) {
        String sql = "SELECT * FROM tab_favorite WHERE rid = ? AND uid = ?";

        Favorite favorite = null;
        try {
            favorite = template.queryForObject(sql, new BeanPropertyRowMapper<>(Favorite.class), rid, uid);
        } catch (DataAccessException e) {
            //e.printStackTrace();
        }
        return favorite;
    }

    public void addFavorite(Integer rid, int uid) throws Exception {
        String sql = "INSERT INTO tab_favorite VALUES (?,?,?)";

        template.update(sql, rid, new Date(), uid);
    }

    public void updateCount(Integer rid, int count) throws Exception {
        String sql = "UPDATE tab_route SET COUNT = COUNT + ? WHERE rid = ?";

        template.update(sql, count, rid);
    }

    public void remFavorite(Integer rid, int uid) {
        String sql = "DELETE FROM tab_favorite WHERE rid = ? AND uid = ?";

        template.update(sql, rid, uid);
    }

    public Integer findMyFavCount(int uid) {
        String sql = "SELECT COUNT(*) FROM tab_favorite WHERE uid = ?";

        return template.queryForObject(sql, Integer.class, uid);
    }

    public List<Map<String, Object>> findMyFavList(int uid, int skipCount, Integer pageSize) {
        String sql = "SELECT * FROM tab_favorite f, tab_route r WHERE f.rid = r.rid AND uid = ? ORDER BY f.date ASC LIMIT ?,?";

        return template.queryForList(sql, uid, skipCount, pageSize);
    }

    public Integer findRankCount(String favName, String lowPrice, String highPrice) {
        List<Object> params = new ArrayList<>();

        String sql = "SELECT COUNT(*) FROM tab_route WHERE rflag = '1'";

        if (favName != null && !"".equals(favName) && !"null".equals(favName)) {
            sql += " AND rname LIKE ?";
            params.add("%" + favName + "%");
        }

        if (lowPrice != null && !"".equals(lowPrice) && !"null".equals(lowPrice)) {
            sql += " AND price >= ?";
            params.add(Double.parseDouble(lowPrice));
        }

        if (highPrice != null && !"".equals(highPrice) && !"null".equals(highPrice)) {
            sql += " AND price <= ?";
            params.add(Double.parseDouble(highPrice));
        }

        return template.queryForObject(sql, Integer.class, params.toArray());
    }

    public List<Route> findRankList(String favName, String lowPrice, String highPrice, int skipCount, Integer pageSize) {
        List<Object> params = new ArrayList<>();

        String sql = "SELECT * FROM tab_route WHERE rflag = '1'";

        if (favName != null && !"".equals(favName) && !"null".equals(favName)) {
            sql += " AND rname LIKE ?";
            params.add("%" + favName + "%");
        }

        if (lowPrice != null && !"".equals(lowPrice) && !"null".equals(lowPrice)) {
            sql += " AND price >= ?";
            params.add(Double.parseDouble(lowPrice));
        }

        if (highPrice != null && !"".equals(highPrice) && !"null".equals(highPrice)) {
            sql += " AND price <= ?";
            params.add(Double.parseDouble(highPrice));
        }

        sql += " ORDER BY COUNT DESC LIMIT ?, ?";
        params.add(skipCount);
        params.add(pageSize);

        return template.query(sql, new BeanPropertyRowMapper<>(Route.class), params.toArray());
    }
}
