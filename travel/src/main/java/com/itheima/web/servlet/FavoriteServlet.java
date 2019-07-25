package com.itheima.web.servlet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itheima.domain.PageBean;
import com.itheima.domain.ResultInfo;
import com.itheima.domain.User;
import com.itheima.service.FavoriteService;
import com.itheima.service.RouteService;

@WebServlet("/favorite")
public class FavoriteServlet extends BaseServlet {
    private FavoriteService service = new FavoriteService();
    private RouteService routeService = new RouteService();

    public void isEdit(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String data;
        ResultInfo info = null;
        ObjectMapper mapper = new ObjectMapper();
        HashMap<String, Boolean> map = new HashMap<>();

        try {
            User user = (User) req.getSession().getAttribute("user");

            if (user == null) {
                map.put("isEdit", true);
                info = new ResultInfo(true, map, "未登录，可以点击");
            } else {
                Integer rid = Integer.valueOf(req.getParameter("rid"));
                boolean isFavorited = service.isFavorited(rid, user.getUid());

                if (isFavorited) {
                    map.put("isEdit", false);
                    info = new ResultInfo(true, map, "已登录，已收藏，不可点击");
                } else {
                    map.put("isEdit", true);
                    info = new ResultInfo(true, map, "已登录，但未收藏，可以点击");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            info = new ResultInfo(false, "获取信息失败");
        } finally {
            data = mapper.writeValueAsString(info);
            resp.getWriter().print(data);
        }
    }

    public void addFavorite(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String data;
        ResultInfo info = null;
        ObjectMapper mapper = new ObjectMapper();
        HashMap<Object, Object> resultMap = new HashMap<>();

        try {
            User user = (User) req.getSession().getAttribute("user");

            if (user == null) {
                resultMap.put("isLogin", false);
                info = new ResultInfo(true, resultMap, "未登录状态");
            } else {
                resultMap.put("isLogin", true);
                Integer rid = Integer.valueOf(req.getParameter("rid"));

                service.addFavorite(rid, user.getUid());

                Map<String, Object> map = routeService.findByRid(rid.toString());
                Integer count = (Integer) map.get("count");

                resultMap.put("count", count);

                info = new ResultInfo(true, resultMap, "收藏成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
            info = new ResultInfo(false, "收藏失败");
        } finally {
            data = mapper.writeValueAsString(info);
            // System.out.println(data);
            resp.getWriter().print(data);
        }
    }

    public void remFavorite(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        String data;
        ResultInfo info = null;
        ObjectMapper mapper = new ObjectMapper();

        try {
            Integer rid = Integer.valueOf(req.getParameter("rid"));
            User user = (User) req.getSession().getAttribute("user");

            service.remFavorite(rid, user.getUid());

            Map<String, Object> map = routeService.findByRid(rid.toString());
            Integer count = (Integer) map.get("count");

            info = new ResultInfo(true, count, "取消收藏成功");
        } catch (NumberFormatException e) {
            e.printStackTrace();
            info = new ResultInfo(false, "取消收藏失败");
        } finally {
            data = mapper.writeValueAsString(info);
            System.out.println(data);
            resp.getWriter().print(data);

        }

    }

    public void showMyFav(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String data;
        ResultInfo info = null;
        ObjectMapper mapper = new ObjectMapper();

        try {
            User user = (User) req.getSession().getAttribute("user");
            Integer curPage = Integer.valueOf(req.getParameter("curPage"));

            PageBean pageBean = service.showMyFav(user.getUid(), curPage);
            info = new ResultInfo(true, pageBean, "收藏信息获取成功");
        } catch (Exception e) {
            e.printStackTrace();
            info = new ResultInfo(false, "收藏信息获取失败");
        } finally {
            data = mapper.writeValueAsString(info);
            resp.getWriter().print(data);
        }
    }

    public void showFavRank(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String data;
        ResultInfo info = null;
        ObjectMapper mapper = new ObjectMapper();

        try {
            Integer curPage = Integer.valueOf(req.getParameter("curPage"));
            String favName = req.getParameter("favName");
            String lowPrice = req.getParameter("lowPrice");
            String highPrice = req.getParameter("highPrice");

            PageBean pageBean = service.showFavRank(curPage, favName, lowPrice, highPrice);

            info = new ResultInfo(true, pageBean, "排行榜信息获取成功");
        } catch (Exception e) {
            e.printStackTrace();
            info = new ResultInfo(false, "排行榜信息获取失败");
        } finally {
            data = mapper.writeValueAsString(info);
            System.out.println(data);
            resp.getWriter().print(data);
        }
    }

}
