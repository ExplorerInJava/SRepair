package top.xiaohang456.srepair.dao;

import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import top.xiaohang456.srepair.model.Orders;
import top.xiaohang456.srepair.model.User;

import java.util.ArrayList;
import java.util.List;

@Component
public class OrderDao extends BaseDao<Orders> {

    public List<Orders> findByStatus(int status) {
        return (List<Orders>) this.getHibernateTemplate().find("from Orders o where o.status = ?", status);
    }

    public List<Orders> findByUserId(int userId) {
        return (List<Orders>) this.getHibernateTemplate().find("from Orders o where o.userId = ?", userId);
    }

    public Orders loadByOrderId(String uuid) {
        List<Orders> orders = (List<Orders>) this.getHibernateTemplate().find("from Orders o where o.orderId = ?", uuid);
        return orders.isEmpty() ? null : orders.get(0);
    }


    //根据三个状态、数量倒序取出
    public List<Orders> loadByStatusNumberDesc(String start, String status1, String status2, String status3, String number) {
        List<Orders> orders = (List<Orders>) this.getHibernateTemplate().find("from Orders o where status = ? or status = ? or status = ? order by id desc ", new Object[]{status1,status2,status3});
        if (Integer.parseInt(start) >= orders.size()) {
            return null;
        }
        return orders.size() >= Integer.parseInt(start) + Integer.parseInt(number) ? orders.subList(Integer.parseInt(start),Integer.parseInt(start) + Integer.parseInt(number))
                : orders.subList(Integer.parseInt(start), orders.size());
    }

    //根据一个状态、数量倒序取出
    public List<Orders> loadByStatusNumberDesc(String start, String status, String number) {
        List<Orders> orders = (List<Orders>) this.getHibernateTemplate().find("from Orders o where status = ? order by id desc ", status );
        if (Integer.parseInt(start) >= orders.size()) {
            return null;
        }
        return orders.size() >= Integer.parseInt(start) + Integer.parseInt(number) ? orders.subList(Integer.parseInt(start),Integer.parseInt(start) + Integer.parseInt(number))
                : orders.subList(Integer.parseInt(start), orders.size());
    }

    //根据一个状态倒序取出,my首页统计不同类别订单总量的时候使用。
    public List<Orders> loadByStatus(String status) {
        List<Orders> orders = (List<Orders>) this.getHibernateTemplate().find("from Orders o where status = ?", status);
        return orders;
    }

    //根据数量倒序取出
    public List<Orders> loadAllNumberDesc(String start, String number) {
        List<Orders> orders = (List<Orders>) this.getHibernateTemplate().find("from Orders o order by id desc ");
        if (Integer.parseInt(start) >= orders.size()) {
            return null;
        }
        return orders.size() >= Integer.parseInt(start) + Integer.parseInt(number) ? orders.subList(Integer.parseInt(start),Integer.parseInt(start) + Integer.parseInt(number))
                : orders.subList(Integer.parseInt(start), orders.size());
    }

    //根据数量倒序取出
    public List<Orders> loadAll() {
        List<Orders> orders = (List<Orders>) this.getHibernateTemplate().find("from Orders o ");
        return orders;
    }


    /*      以下为用户加载自己订单用到的       */

    //记载所有
    public List<Orders> user_loadAll(User user) {
        List<Orders> orders = (List<Orders>) this.getHibernateTemplate().find("from Orders o where UserId = ? ", user.getId());
        return orders;
    }

    //根据一个状态加载所有
    public List<Orders> user_loadByStatuts(User user, String status) {
        List<Orders> orders = (List<Orders>) this.getHibernateTemplate().find("from Orders o where userId = ? and status = " + status, user.getId());
        return orders;
    }

    //根据一个状态加载部分
    public List<Orders> user_loacByStatusNumber(User user, String start, String status, String number) {
        List<Orders> orders = (List<Orders>) this.getHibernateTemplate().find("from Orders o where o.status = ? and userId = " + user.getId() + " order by id desc ", status );
        if (Integer.parseInt(start) >= orders.size()) {
            return null;
        }
        return orders.size() >= Integer.parseInt(start) + Integer.parseInt(number) ? orders.subList(Integer.parseInt(start),Integer.parseInt(start) + Integer.parseInt(number))
                : orders.subList(Integer.parseInt(start), orders.size());
    }

    //根据三个状态加载部分
    public List<Orders> user_loacByStatusNumber(User user, String start, String status1, String status2, String status3, String status4, String number) {
        List<Orders> orders = (List<Orders>) this.getHibernateTemplate().find("from Orders o where  status not like 4 and userId = " + user.getId() + " order by id desc ");
        if (Integer.parseInt(start) >= orders.size()) {
            return null;
        }
        return orders.size() >= Integer.parseInt(start) + Integer.parseInt(number) ? orders.subList(Integer.parseInt(start),Integer.parseInt(start) + Integer.parseInt(number))
                : orders.subList(Integer.parseInt(start), orders.size());
    }
}
