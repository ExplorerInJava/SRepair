package top.xiaohang456.srepair.Util;

import top.xiaohang456.srepair.service.Admin;

import java.util.ArrayList;
import java.util.List;

public class AdminList {
    public static List<Admin> admins = new ArrayList<Admin>();

    static {
        //如果需要增设一个管理员账号，执行以下操作即可：
        Admin admin1 = new Admin();
        admin1.setAccount("12341234");
        admin1.setPassword("12341234");
        admins.add(admin1);
        //添加第二个管理员账号：
        Admin admin2 = new Admin();
        admin2.setAccount("88888888");
        admin2.setPassword("88888888");
        admins.add(admin2);
    }

    public static String getString() {
        return "AdminList{" +
                "admins=" + admins +
                '}';
    }
}
