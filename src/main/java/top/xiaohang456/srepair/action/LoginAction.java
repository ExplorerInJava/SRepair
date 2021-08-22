package top.xiaohang456.srepair.action;

import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.ServletActionContext;
import top.xiaohang456.srepair.Util.AdminList;
import top.xiaohang456.srepair.dao.UserDao;
import top.xiaohang456.srepair.model.User;
import top.xiaohang456.srepair.service.Admin;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class LoginAction extends ActionSupport {
    private String account;
    private UserDao userDao;
    private String password;
    private HttpServletResponse response = ServletActionContext.getResponse();

    @Override
    public String execute() throws IOException {
        response.setCharacterEncoding("utf-8");
        List<Admin> admins = AdminList.admins;
        System.out.println(AdminList.getString());
        for (Admin admin : admins) {
            if (account.equals(admin.getAccount())) {
                if (password.equals(admin.getPassword())) {
                    response.getWriter().write("{\"data\": \"true\", \"msg\" : \"欢迎管理员登录！\", \"status\":\"" + 0 + "\"}");
                    return NONE;
                } else {
                    response.getWriter().write("{\"data\": \"false\", \"msg\" : \"管理员密码错误！\", \"status\":\"" + 1 + "\"}");
                    return NONE;
                }
            }
        }

        User user = userDao.findByAccount(account);
        if (user == null) {
            response.getWriter().write("{\"data\": \"false\", \"msg\" : \"用户不存在,请检查您的账号\", \"status\":\"" + 1 + "\"}");
            return NONE;
        } else if (user.getPassword().equals(password)) {
            response.getWriter().write("{\"data\": \"false\", \"msg\" : \"login\", \"status\":\"" + 0 + "\"}");
            return NONE;
        } else {
            response.getWriter().write("{\"data\": \"false\", \"msg\" : \"密码错误，请重新输入\", \"status\":\"" + 1 + "\"}");
        }

        return NONE;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserDao getUserDao() {
        return userDao;
    }

    @Resource
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }
}
