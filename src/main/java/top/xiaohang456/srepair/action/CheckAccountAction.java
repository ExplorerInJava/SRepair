package top.xiaohang456.srepair.action;


import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.ServletActionContext;
import top.xiaohang456.srepair.dao.UserDao;
import top.xiaohang456.srepair.model.User;
import top.xiaohang456.srepair.service.Admin;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CheckAccountAction extends ActionSupport {
    private HttpServletResponse response = ServletActionContext.getResponse();
    private String account;
    private UserDao userDao;


    @Override
    public String execute() throws IOException {
        response.setCharacterEncoding("utf-8");
        if (account == null || account.trim().equals("")) {
            response.getWriter().write("{\"data\": \"false\", \"msg\" : \"查询错误！\", \"status\":\"" + 1 + "\"}");
            return NONE;
        } /*else if (account.equals(Admin.getAccount())) {
            response.getWriter().write("{\"data\": \"admin\", \"msg\" : \"exist\", \"status\":\"" + 0 + "\"}");
            return NONE;
        }*/
        User user = (User) userDao.findByAccount(account);
        if (user != null) {
            response.getWriter().write("{\"data\": \"1\", \"msg\" : \"exist\", \"status\":\"" + 1 + "\"}");
        } else {
            response.getWriter().write("{\"data\": \"1\", \"msg\" : \"notExist\", \"status\":\"" + 0 + "\"}");
        }
        return NONE;
    }

    public UserDao getUserDao() {
        return userDao;
    }

    @Resource
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }
}
