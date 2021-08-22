package top.xiaohang456.srepair.action;

import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.ServletActionContext;
import top.xiaohang456.srepair.dao.UserDao;
import top.xiaohang456.srepair.model.User;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class ChangePasswordAction extends ActionSupport {
    private HttpServletResponse response = ServletActionContext.getResponse();
    private String account;
    private String password;
    private UserDao dao;


    @Override
    public String execute() throws IOException {
        response.setCharacterEncoding("utf-8");
        User user = dao.findByAccount(account);
        user.setPassword(password);
        dao.update(user);
        response.getWriter().write("{\"data\": \"0\", \"msg\":\"修改密码成功\", \"status\":\"" + 0 + "\"}");

        return NONE;
    }

    public UserDao getDao() {
        return dao;
    }

    @Resource
    public void setDao(UserDao dao) {
        this.dao = dao;
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
}
