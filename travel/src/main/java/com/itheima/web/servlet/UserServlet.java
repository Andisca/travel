package com.itheima.web.servlet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itheima.domain.ResultInfo;
import com.itheima.service.UserService;
import com.itheima.domain.User;
import org.apache.commons.beanutils.BeanUtils;

@WebServlet("/user")
public class UserServlet extends BaseServlet {

    private UserService service = new UserService();

    public void register(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String data;
        ResultInfo info = null;
        ObjectMapper mapper = new ObjectMapper();

        try {
            Map<String, String[]> map = req.getParameterMap();

            String[] check = map.get("check");
            String code = (String) req.getSession().getAttribute("code");

            boolean flag = checkCode(check[0], code);

            if (flag == false) {
                info = new ResultInfo(false, "验证码错误");
                return;
            }

            User user = new User();
            BeanUtils.populate(user, map);

            service.register(user);

            info = new ResultInfo(true, "注册成功");

        } catch (Exception e) {
            info = new ResultInfo(false, "注册失败");
            e.printStackTrace();
        } finally {
            data = mapper.writeValueAsString(info);
            resp.getWriter().print(data);
        }
    }

    public void active(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String code = req.getParameter("code");

        boolean flag = service.active(code);

        if (flag) {
            resp.sendRedirect("login.html");
        } else {
            resp.getWriter().print("激活失败");
        }
    }

    public void login(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String data;
        ResultInfo info = null;
        ObjectMapper mapper = new ObjectMapper();

        try {
            String username = req.getParameter("username");

            String password = req.getParameter("password");

            String check = req.getParameter("check");
            String code = (String) req.getSession().getAttribute("code");

            boolean flag = checkCode(check, code);

            if (flag == false) {
                info = new ResultInfo(false, "验证码错误");
                return;
            }

            User user = service.login(username, password);

            if (user != null) {
                if ("Y".equals(user.getStatus())) {
                    req.getSession().setAttribute("user", user);
                    info = new ResultInfo(true, "登录成功");
                } else {
                    info = new ResultInfo(false, "您还未激活，请先激活后再登录！");
                }
            } else {
                info = new ResultInfo(false, "用户名或密码错误");
            }

        } catch (Exception e) {
            info = new ResultInfo(false, "登录失败");
            e.printStackTrace();
        } finally {
            data = mapper.writeValueAsString(info);
            resp.getWriter().print(data);
        }
    }

    public void loginInfo(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        ResultInfo info;

        User user = (User) req.getSession().getAttribute("user");

        if (user != null) {
            info = new ResultInfo(true, user.getUsername(), "已登录状态");
        } else {
            info = new ResultInfo(false, null, "未登录状态");
        }

        ObjectMapper mapper = new ObjectMapper();
        String data = mapper.writeValueAsString(info);

        resp.getWriter().print(data);
    }

    public void logout(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.getSession().removeAttribute("user");

        resp.sendRedirect("login.html");
    }

    private boolean checkCode(String check, String code) {
        boolean flag = false;

        if (code.equalsIgnoreCase(check)) {
            flag = true;
        }

        return flag;
    }
}
