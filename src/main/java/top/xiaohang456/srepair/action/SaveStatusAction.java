package top.xiaohang456.srepair.action;

import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.ServletActionContext;
import top.xiaohang456.srepair.dao.OrderDao;
import top.xiaohang456.srepair.model.Orders;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;

public class SaveStatusAction extends ActionSupport {
    private HttpServletResponse response = ServletActionContext.getResponse();
    private OrderDao orderDao;
    private String orderId;
    private String status;

    @Override
    public String execute() throws IOException {
        response.setCharacterEncoding("utf-8");
        if (orderId != null && status != null) {
            Orders order = orderDao.loadByOrderId(orderId);
            order.setStatus(status);
            order.setLast_date(new Timestamp(System.currentTimeMillis()).toString());

            orderDao.update(order);
            response.getWriter().write("{\"data\": \"1\", \"msg\":\"保存状态成功\", \"status\":\"" + 0 + "\"}");
        } else {
            response.getWriter().write("{\"data\": \"1\", \"msg\":\"保存状态失败\", \"status\":\"" + 1 + "\"}");
        }

        return NONE;
    }

    public OrderDao getOrderDao() {
        return orderDao;
    }

    @Resource
    public void setOrderDao(OrderDao orderDao) {
        this.orderDao = orderDao;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
