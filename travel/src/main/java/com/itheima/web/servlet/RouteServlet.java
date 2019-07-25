package com.itheima.web.servlet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itheima.domain.PageBean;
import com.itheima.domain.ResultInfo;
import com.itheima.domain.Route;
import com.itheima.service.RouteService;

@WebServlet("/route")
public class RouteServlet extends BaseServlet {

    private RouteService service = new RouteService();

    public void showSelected(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String data;
        ResultInfo info = null;
        ObjectMapper mapper = new ObjectMapper();

        try {
            HashMap<String, List<Route>> map = service.showSelected();
            info = new ResultInfo(true, map, "线路获取成功");
        } catch (Exception e) {
            e.printStackTrace();
            info = new ResultInfo(false, "线路获取失败");
        } finally {
            data = mapper.writeValueAsString(info);
            // System.out.println(data);
            resp.getWriter().print(data);
        }

    }

    public void findByPage(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String cid = req.getParameter("cid");
        Integer curPage = Integer.valueOf(req.getParameter("curPage"));
        String rname = req.getParameter("rname");

        String data;
        ResultInfo info = null;
        ObjectMapper mapper = new ObjectMapper();

        try {
            PageBean pageBean = service.findByPage(cid, curPage,rname);

            info = new ResultInfo(true, pageBean, "分页查询成功");
        } catch (Exception e) {
            e.printStackTrace();
            info = new ResultInfo(false, "分页查询失败");
        } finally {
            data = mapper.writeValueAsString(info);
            resp.getWriter().print(data);
        }
    }

    public void findByRid(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String data;
        ResultInfo info = null;
        ObjectMapper mapper = new ObjectMapper();

        String rid = req.getParameter("rid");

        try {
            Map<String, Object> map = service.findByRid(rid);

            info = new ResultInfo(true,map,"线路详情获取成功");
        } catch (Exception e) {
            e.printStackTrace();
            info = new ResultInfo(false,"线路详情获取失败");
        } finally {
            data = mapper.writeValueAsString(info);
            resp.getWriter().print(data);
        }
    }
}
