package top.xiaohang456.srepair.dao;

import org.springframework.stereotype.Component;
import top.xiaohang456.srepair.model.User;

import java.util.List;
import java.util.Stack;

@Component
public class UserDao extends BaseDao<User> {
    public User findByAccount(String account) {
        List<User> users = (List<User>) this.getHibernateTemplate().find("from User u where account = ? ", account);
        return users.isEmpty() ? null : users.get(0);
    }


}
