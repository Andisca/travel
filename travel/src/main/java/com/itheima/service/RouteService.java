package com.itheima.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.itheima.constant.Constant;
import com.itheima.dao.RouteDao;
import com.itheima.domain.PageBean;
import com.itheima.domain.Route;
import com.itheima.domain.RouteImg;

public class RouteService {
    private RouteDao dao = new RouteDao();

    public HashMap<String, List<Route>> showSelected() {

        List<Route> popularList = dao.findPopular();
        List<Route> newList = dao.findNewest();
        List<Route> themeList = dao.findTheme();

        HashMap<String, List<Route>> map = new HashMap<>();

        map.put("popularList", popularList);
        map.put("newList", newList);
        map.put("themeList", themeList);

        return map;
    }

    public PageBean findByPage(String cid, Integer curPage, String rname) {
        PageBean<Route> pageBean = new PageBean<>();

        pageBean.setCurPage(curPage);

        Integer pageSize = Constant.TRAVEL_PAGE_SIZE;
        pageBean.setPageSize(pageSize);

        Integer totalCount = dao.findCount(cid,rname);
        pageBean.setTotalCount(totalCount);

        Integer totalPage = totalCount % pageSize == 0 ? totalCount / pageSize : totalCount / pageSize + 1;
        pageBean.setTotalPage(totalPage);

        List<Route> list = dao.findList(cid, (curPage - 1) * pageSize, pageSize,rname);
        pageBean.setList(list);

        return pageBean;
    }

    public Map<String, Object> findByRid(String rid) {

        Map<String, Object> map = dao.findByRid(rid);

        List<RouteImg> imgList = dao.findRouteImg(rid);
        map.put("imgList", imgList);

        return map;
    }
}
