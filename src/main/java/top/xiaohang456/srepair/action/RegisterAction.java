package top.xiaohang456.srepair.action;

import com.opensymphony.xwork2.ActionSupport;
import top.xiaohang456.srepair.dao.UserDao;
import top.xiaohang456.srepair.model.User;
import javax.annotation.Resource;
import java.sql.Timestamp;


public class RegisterAction extends ActionSupport {
    private String name;
    private String password;
    private String phone;
    private String nickName;
    private String province;
    private String city;
    private String gender;
    private String avatarUrl;
    private Timestamp timestamp;
    private UserDao dao;


    @Override
    public String execute(){

        User user = new User();
        user.setAccount(name);
        user.setAvatarUrl(avatarUrl);
        user.setProvince(province);
        user.setCity(city);
        user.setNickName(nickName);
        user.setPassword(password);
        user.setPhone(phone);
        user.setSex(gender);
        user.setTimestamp(new Timestamp(System.currentTimeMillis()));
        dao.save(user);
        return NONE;
    }

    public UserDao getDao() {
        return dao;
    }

    @Resource
    public void setDao(UserDao dao) {
        this.dao = dao;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

}
