package com.itheima.service;

import java.util.List;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itheima.constant.Constant;
import com.itheima.dao.CategoryDao;
import com.itheima.domain.Category;
import com.itheima.utils.JedisUtil;
import redis.clients.jedis.Jedis;

public class CategoryService {
    private CategoryDao dao = new CategoryDao();

    public String showCategory() throws Exception {
        Jedis jedis;
        String data;
        try {
            jedis = JedisUtil.getJedis();
            data = getFromRedis(jedis);

            if (data == null) {
                data = getFromMysql();
                SaveToRedis(jedis, data);
                System.out.println("从MySQL中获取数据，再将其存入Redis中");
            } else {
                System.out.println("从Redis中获取数据");
            }
        } catch (Exception e) {
           // e.printStackTrace();
            data = getFromMysql();
            System.out.println("Redis服务器未开启，从MySQL中获取数据");
        }

        return data;
    }

    private void SaveToRedis(Jedis jedis, String data) {
        if (jedis != null) {
            jedis.set(Constant.TRAVEL_CATE_KEY, data);
        }
    }

    private String getFromMysql() throws Exception {

        List<Category> list = dao.showCategory();

        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(list);

    }

    private String getFromRedis(Jedis jedis) {
        String data = null;
        if (jedis != null) {
            data = jedis.get(Constant.TRAVEL_CATE_KEY);
        }
        return data;
    }
}
