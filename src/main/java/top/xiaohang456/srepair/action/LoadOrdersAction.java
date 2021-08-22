package top.xiaohang456.srepair.action;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.ServletActionContext;
import top.xiaohang456.srepair.dao.OrderDao;
import top.xiaohang456.srepair.dao.UserDao;
import top.xiaohang456.srepair.model.Orders;
import top.xiaohang456.srepair.model.User;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class LoadOrdersAction extends ActionSupport {
    private String status1;
    private String status2;
    private String number;
    private String start;
    private OrderDao orderDao;
    private UserDao userDao;
    private List<Orders> orders;
    private String account;
    private HttpServletResponse response = ServletActionContext.getResponse();

    public void loadOrders() throws IOException {
        System.out.println("status1-----" + status1);
        System.out.println("status2-----" + status2);
        System.out.println("number:-----" + number);
        System.out.println("start-------" + start);
        response.setCharacterEncoding("utf-8");
        //当传入两个或两个以上状态时都认为是要加载所有未处理完成订单。即状态为1、2、3、的订单
        if (status1 != null && status2 != null) {
            if (number == null) {
                orders = orderDao.loadByStatusNumberDesc(start, status1, status2, "3", "5");
                if (orders != null) {
                    response.getWriter().write("{\"data\": " + JSON.toJSONString(orders,SerializerFeature.DisableCircularReferenceDetect) + ", \"msg\": \"返回数据成功！\", \"status\":\"" + 0 + "\"}");
                    return;
                }
                response.getWriter().write("{\"data\":null, \"msg\": \"无更多数据！\", \"status\":\"" + 0 + "\"}");
                return;
            } else {
                orders = orderDao.loadByStatusNumberDesc(start , status1, status2, "3", number);
                if (orders != null) {
                    response.getWriter().write("{\"data\": " + JSON.toJSONString(orders,SerializerFeature.DisableCircularReferenceDetect) + ", \"msg\": \"返回数据成功！\", \"status\":\"" + 0 + "\"}");
                    return;
                }
                response.getWriter().write("{\"data\":null, \"msg\": \"无更多数据！\", \"status\":\"" + 0 + "\"}");
                return;
            }

        } else if(status1 != null && status2 == null) {
            if (number == null) {
                orders = orderDao.loadByStatusNumberDesc(start, status1,"5");
                if (orders != null) {
                    response.getWriter().write("{\"data\": " + JSON.toJSONString(orders,SerializerFeature.DisableCircularReferenceDetect) + ", \"msg\": \"返回数据成功！\", \"status\":\"" + 0 + "\"}");
                    return;
                }
                response.getWriter().write("{\"data\":null, \"msg\": \"无更多数据！\", \"status\":\"" + 0 + "\"}");
                return;
            } else {
                orders = orderDao.loadByStatusNumberDesc(start, status1, number);
                if (orders != null) {
                    response.getWriter().write("{\"data\": " + JSON.toJSONString(orders,SerializerFeature.DisableCircularReferenceDetect) + ", \"msg\": \"返回数据成功！\", \"status\":\"" + 0 + "\"}");
                    return;
                }
                response.getWriter().write("{\"data\":null, \"msg\": \"无更多数据！\", \"status\":\"" + 0 + "\"}");
                return;
            }
        } else if(status1 == null && status2 == null) {
            if (number == null) {
                orders = orderDao.loadAllNumberDesc(start, "5");
                System.out.println(JSON.toJSONString(orders));
                if (orders != null) {
                    response.getWriter().write("{\"data\": " + JSON.toJSONString(orders,SerializerFeature.DisableCircularReferenceDetect) + ", \"msg\": \"返回数据成功！\", \"status\":\"" + 0 + "\"}");
                    return;
                }
                response.getWriter().write("{\"data\":null, \"msg\": \"无更多数据！\", \"status\":\"" + 0 + "\"}");
                return;
            } else {
                orders = orderDao.loadAllNumberDesc(start,number);
                response.getWriter().write("{\"data\": " + JSON.toJSONString(orders,SerializerFeature.DisableCircularReferenceDetect) + ", \"msg\": \"返回数据成功！\", \"status\":\"" + 0 + "\"}");
                return;
            }

        } else {
            response.getWriter().write("{\"data\": \"null\", \"msg\": \"抱歉，传入参数错误！\", \"status\":\"" + 0 + "\"}");
            return;
        }
    }


    public void user_loadOrders() throws Exception {
        System.out.println("status1-----" + status1);
        System.out.println("status2-----" + status2);
        System.out.println("number:-----" + number);
        System.out.println("start-------" + start);
        System.out.println("account-----" + account);
        response.setCharacterEncoding("utf-8");
        //根据一个状态加载订单
        if (status1 != null && status2 == null) {
            if(number == null) {
                User user = userDao.findByAccount(account);
                orders = orderDao.user_loacByStatusNumber(user, start, status1, "5");
                System.out.println(JSON.toJSONString(orders));
                if (orders != null) {
                    response.getWriter().write("{\"data\": " + JSON.toJSONString(orders,SerializerFeature.DisableCircularReferenceDetect) + ", \"msg\": \"返回数据成功！\", \"status\":\"" + 0 + "\"}");
                    return;
                }
                response.getWriter().write("{\"data\":null, \"msg\": \"无更多数据！\", \"status\":\"" + 0 + "\"}");
                return;
            } else {
                User user = userDao.findByAccount(account);
                orders = orderDao.user_loacByStatusNumber(user, start, status1, number);
                System.out.println(JSON.toJSONString(orders));
                if (orders != null) {
                    response.getWriter().write("{\"data\": " + JSON.toJSONString(orders,SerializerFeature.DisableCircularReferenceDetect) + ", \"msg\": \"返回数据成功！\", \"status\":\"" + 0 + "\"}");
                    return;
                }
                response.getWriter().write("{\"data\":null, \"msg\": \"无更多数据！\", \"status\":\"" + 0 + "\"}");
                return;
            }
        } else {//根据四个状态加载orders
            if(number == null) {
                User user = userDao.findByAccount(account);
                orders = orderDao.user_loacByStatusNumber(user, start, status1, "2", "3", "0", "5");
                System.out.println(JSON.toJSONString(orders));
                if (orders != null) {
                    response.getWriter().write("{\"data\": " + JSON.toJSONString(orders,SerializerFeature.DisableCircularReferenceDetect) + ", \"msg\": \"返回数据成功！\", \"status\":\"" + 0 + "\"}");
                    return;
                }
                response.getWriter().write("{\"data\":null, \"msg\": \"无更多数据！\", \"status\":\"" + 0 + "\"}");
                return;
            } else {
                User user = userDao.findByAccount(account);
                orders = orderDao.user_loacByStatusNumber(user, start, status1, "2", "3", "0", number);
                System.out.println(JSON.toJSONString(orders));
                if (orders != null) {
                    response.getWriter().write("{\"data\": " + JSON.toJSONString(orders,SerializerFeature.DisableCircularReferenceDetect) + ", \"msg\": \"返回数据成功！\", \"status\":\"" + 0 + "\"}");
                    return;
                }
                response.getWriter().write("{\"data\":null, \"msg\": \"无更多数据！\", \"status\":\"" + 0 + "\"}");
                return;
            }

        }

    }

    public String getStatus1() {
        return status1;
    }

    public void setStatus1(String status1) {
        this.status1 = status1;
    }

    public String getStatus2() {
        return status2;
    }

    public void setStatus2(String status2) {
        this.status2 = status2;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public OrderDao getOrderDao() {
        return orderDao;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    @Resource
    public void setOrderDao(OrderDao orderDao) {
        this.orderDao = orderDao;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public UserDao getUserDao() {
        return userDao;
    }

    @Resource
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }
}


