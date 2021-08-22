package top.xiaohang456.srepair.action;


import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.ServletActionContext;
import top.xiaohang456.srepair.dao.UserDao;
import top.xiaohang456.srepair.model.User;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CheckAnswerAction extends ActionSupport {
    private HttpServletResponse response = ServletActionContext.getResponse();
    private String account;
    private String answer;
    private UserDao userDao;
    private String question;

    @Override
    public String execute() throws IOException {
        response.setCharacterEncoding("utf-8");
        if (account ==null || account.trim().equals("")) {
            return NONE;
        }
        User user = (User) userDao.findByAccount(account);
        if (question != null && question.equals("yes")) {
            response.getWriter().write("{\"data\": \"" + user.getQuestion() + "\", \"msg\" : \"问题查询成功\", \"status\":\"" + 1 + "\"}");
        } else {
            if (answer.equals(user.getAnswer())){
                response.getWriter().write("{\"data\": \"" + user.getPassword() + "\", \"msg\" : \"答案正确\", \"status\":\"" + 0 + "\"}");
            } else {
                response.getWriter().write("{\"data\": \"1\", \"msg\" : \"答案错误\", \"status\":\"" + 0 + "\"}");
            }
        }
        return NONE;
    }

    public UserDao getUserDao() {
        return userDao;
    }

    @Resource
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }
}
