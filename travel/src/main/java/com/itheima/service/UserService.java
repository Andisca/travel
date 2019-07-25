package com.itheima.service;

import com.itheima.dao.UserDao;
import com.itheima.domain.User;
import com.itheima.utils.MailUtil;
import com.itheima.utils.Md5Util;
import com.itheima.utils.UUIDUtil;

public class UserService {
    private UserDao dao = new UserDao();

    public void register(User user) throws Exception {

        user.setPassword(Md5Util.encodeByMd5(user.getPassword()));
        user.setStatus("N");
        user.setCode(UUIDUtil.getUuid());

        dao.save(user);

        MailUtil.sendMail(user.getEmail(), "尊敬的 " + user.getName() + " 先生/女士：您好，欢迎您注册黑马旅游网，为保障你的隐私安全，请先" +
                "<a href='http://localhost:8080/travel/user?action=active&code=" + user.getCode() + "'>激活账号</a>，激活成功后方可进行登录，感谢您的配合！");
    }

    public boolean active(String code) {
        int rows = dao.active(code);

        return rows > 0;
    }

    public User login(String username, String password) throws Exception {
        password = Md5Util.encodeByMd5(password);

        return dao.findUser(username,password);
    }
}
