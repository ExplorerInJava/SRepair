package top.xiaohang456.srepair.action;

import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.ServletActionContext;
import top.xiaohang456.srepair.dao.OrderDao;
import top.xiaohang456.srepair.dao.UserDao;
import top.xiaohang456.srepair.model.User;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CountAction extends ActionSupport {
    private HttpServletResponse response = ServletActionContext.getResponse();
    private OrderDao orderDao;
    private UserDao userDao;
    private String isAdmin;
    private String account;

    @Override
    public String execute() throws IOException {
        response.setCharacterEncoding("utf-8");
        if (isAdmin.equals("true")) {
            int all=0, fixing=0, fixed=0;
            all = orderDao.loadAll().size();
            fixing = orderDao.loadByStatus("1").size() + orderDao.loadByStatus("2").size() + orderDao.loadByStatus("0").size() + orderDao.loadByStatus("3").size();
            fixed = orderDao.loadByStatus("4").size();
            response.getWriter().write("{\"data\": \"1\", \"msg\":\"查询成功\", \"all\":\"" + all + "\", " +
                    "\"fixing\":\""+ fixing +"\", \"fixed\":\""+ fixed +"\", \"status\":\"" + 0 + "\"}");
            return NONE;
        } else {
            int all=0, fixing=0, fixed=0;
            User user = userDao.findByAccount(account);
            all = orderDao.user_loadAll(user).size();
            fixing = orderDao.user_loadByStatuts(user,"0").size() + orderDao.user_loadByStatuts(user,"1").size()
                    + orderDao.user_loadByStatuts(user,"2").size() + orderDao.user_loadByStatuts(user,"3").size();
            fixed = orderDao.user_loadByStatuts(user,"4").size();;
            response.getWriter().write("{\"data\": \"1\", \"msg\":\"查询成功\", \"all\":\"" + all + "\", " +
                    "\"fixing\":\""+ fixing +"\", \"fixed\":\""+ fixed +"\", \"status\":\"" + 0 + "\"}");
            return NONE;
        }
    }


    public HttpServletResponse getResponse() {
        return response;
    }

    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }

    public OrderDao getOrderDao() {
        return orderDao;
    }

    @Resource
    public void setOrderDao(OrderDao orderDao) {
        this.orderDao = orderDao;
    }


    public UserDao getUserDao() {
        return userDao;
    }

    @Resource
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public String getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(String isAdmin) {
        this.isAdmin = isAdmin;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }
}
