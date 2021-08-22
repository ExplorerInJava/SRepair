package top.xiaohang456.srepair.action;

import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.ServletActionContext;
import top.xiaohang456.srepair.Util.AdminList;
import top.xiaohang456.srepair.service.Admin;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AdminAction extends ActionSupport {
    private HttpServletResponse response = ServletActionContext.getResponse();
    private String password;
    private String question;
    private String account;
    private String answer;

    public void changePassword() throws IOException {
        response.setCharacterEncoding("utf-8");
        Admin a = null;
        int index = -1;
        if (password != null) {
            for (Admin admin : AdminList.admins) {
                if (admin.getAccount().equals(account)) {
                    index = AdminList.admins.indexOf(admin);
                    admin.setPassword(password);
                    a = admin;
                }
            } if(a != null && index != -1) {
                AdminList.admins.set(index,a);
                response.getWriter().write("{\"data\": \"0\", \"msg\" : \"修改密码成功\", \"status\":\"" + 1 + "\"}");
            }
        } else {
            response.getWriter().write("{\"data\": \"1\", \"msg\" : \"修改密码失败\", \"status\":\"" + 0 + "\"}");
        }
    }

    public void changeQuestion() throws IOException {
        response.setCharacterEncoding("utf-8");
        Admin a = null;
        int index = 0;
        if (question != null && answer!= null) {
            for (Admin admin : AdminList.admins) {
                if (admin.getAccount().equals(account)) {
                    index = AdminList.admins.indexOf(admin);
                    admin.setQuestion(question);
                    admin.setPassword(answer);
                    a = admin;
                }
            }
            AdminList.admins.set(index,a);
            response.getWriter().write("{\"data\": \"0\", \"msg\" : \"修改密码问题成功\", \"status\":\"" + 1 + "\"}");
        } else {
            response.getWriter().write("{\"data\": \"1\", \"msg\" : \"修改密码问题失败\", \"status\":\"" + 0 + "\"}");
        }
    }

    public void checkAnswer() throws IOException {
        response.setCharacterEncoding("utf-8");
        System.out.println("checkAnswer");
        if (question != null && "yes".equals(question)) {
            for (Admin admin : AdminList.admins) {
                if (admin.getAccount().equals(account)) {
                    response.getWriter().write("{\"data\": \"" + admin.getQuestion() + "\", \"msg\" : \"返回问题成功\", \"status\":\"" + 1 + "\"}");
                    return;
                }
            }
        } else if (answer != null) {
            for (Admin admin : AdminList.admins) {
                if (admin.getAccount().equals(account) && admin.getAnswer().equals(answer)) {
                    response.getWriter().write("{\"data\": \"" + admin.getPassword() + "\", \"msg\" : \"返回密码成功\", \"status\":\"" + 1 + "\"}");
                    return;
                }
            }
        } else {
            response.getWriter().write("{\"data\": \"1\", \"msg\" : \"查询失败\", \"status\":\"" + 0 + "\"}");
        }
    }



    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }
}
