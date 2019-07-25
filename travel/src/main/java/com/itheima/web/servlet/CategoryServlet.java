package com.itheima.web.servlet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itheima.domain.ResultInfo;
import com.itheima.service.CategoryService;

@WebServlet("/category")
public class CategoryServlet extends BaseServlet {
    private CategoryService service = new CategoryService();

    public void showCategory(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String data;
        ResultInfo info = null;
        ObjectMapper mapper = new ObjectMapper();

        try {
            String category = service.showCategory();
            info = new ResultInfo(true, category, "类别获取成功");
        } catch (Exception e) {
            e.printStackTrace();
            info = new ResultInfo(false, "类别获取失败");
        } finally {
            data = mapper.writeValueAsString(info);
            resp.getWriter().print(data);
        }

    }
}
