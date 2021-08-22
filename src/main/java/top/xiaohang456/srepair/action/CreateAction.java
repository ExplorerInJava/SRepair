package top.xiaohang456.srepair.action;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.dispatcher.multipart.StrutsUploadedFile;
import sun.misc.BASE64Encoder;
import top.xiaohang456.srepair.dao.OrderDao;
import top.xiaohang456.srepair.dao.UserDao;
import top.xiaohang456.srepair.model.Orders;
import top.xiaohang456.srepair.model.User;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.*;
import java.sql.Timestamp;
import java.util.UUID;

public class CreateAction extends ActionSupport {
    private HttpServletResponse response = ServletActionContext.getResponse();
    private HttpServletRequest request = ServletActionContext.getRequest();
    private StrutsUploadedFile orderImage;
    private String orderImageContentType;
    private String orderImageFileName;
    private String address;
    private String detailAddress;
    private String detail;
    private String id;
    private String account;
    private OrderDao dao;
    private String uuid;
    private UserDao userDao;
    private String imgNum;
    private static Logger log = Logger.getLogger(User.class.getClass());


    @Override
    public String execute() throws IOException {
        response.setCharacterEncoding("utf-8");
        System.out.println(orderImage);
        System.out.println(orderImageContentType);
        System.out.println(orderImageFileName);
        System.out.println(address);
        System.out.println(detailAddress);
        System.out.println(detail);
        System.out.println(imgNum);
        System.out.println(id);
        System.out.println(account);


        if (id.equals("0")) {
            System.out.println("开始处理第一请求");
            Orders order = new Orders();
            order.setAddress(address);
            order.setDetail(detail);
            order.setDetailAddress(detailAddress);
            order.setStatus("0");
            String date = new Timestamp(System.currentTimeMillis()).toString();
            date = date.substring(0,date.indexOf("."));
            order.setStart_date(date);
            order.setImages("");
            User user = userDao.findByAccount(account);
            order.setUser(user);
            String uuid = UUID.randomUUID().toString().substring(0,8);
            order.setOrderId(uuid);

            //图片上传图床;
            /*System.out.println(this.getBase64(orderImage).substring(0,50));
            System.out.println(new String(this.getBase64(orderImage).getBytes(),"utf-8").substring(0,50));
            String param = "key=123456&onlyUrl=1&Base64=" + new String(this.getBase64(orderImage).getBytes(),"utf-8");
            System.out.println(param);
            String url = "http://xiaohang456.top:8080/api";
            String msg = this.doGetJson(url, param).toString();
            log.error(msg);
            System.out.println(msg);*/


            //图片存入本地文件系统;
            FileInputStream fi = new FileInputStream(orderImage.getContent());
            String path = "/order/" + account +"/" + uuid; ///usr/local/tomcat9/webapps/SRepair
            System.out.println(path);
            String name = id + ".jpg";
            this.saveToImgByInputStream(fi, path, name);
            order.setLocal_path("F:\\javaEE-IDEA工作站9月\\SRepair\\src\\main\\webapp\\order" + account +"\\" + uuid + "\\");
            order.setImages(order.getImages().concat(name));
            dao.save(order);
            String resp1 = "{\"data\": \"last\", \"msg\": \"" + uuid + "\", \"status\":\"" + 0 + "\"}";
            response.getWriter().write(resp1);


        } else if (uuid !=null && !uuid.equals("")){
            System.out.println("UUID:-----" + uuid);
            System.out.println("处理后续请求");
            Orders order = dao.loadByOrderId(uuid);
            FileInputStream fi = new FileInputStream(orderImage.getContent());
            String path = "F:\\javaEE-IDEA工作站9月\\SRepair\\src\\main\\webapp\\order" + account +"\\" + uuid;
            System.out.println(path);
            String name = id + ".jpg";
            this.saveToImgByInputStream(fi, path, name);
            order.setImages(order.getImages().concat("," + name));
            dao.update(order);
            int counter = Integer.parseInt(id) + 1;
            response.getWriter().write("{\"data\": \"2\", \"msg\":\"第" + counter + "个上传成功\", \"status\":\"" + 0 + "\"}");

        } else {
            response.getWriter().write("{\"data\": \"0\", \"msg\":\"Failed in uploading， caused by the empty set of uuid\", \"status\":\"" + 1 + "\"}");
        }

        return NONE;
    }

    private JSONObject sendPostUrl(String url, String param){
        PrintWriter out = null;
        BufferedReader in = null;
        JSONObject jsonObject = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流（设置请求编码为UTF-8）
            out = new PrintWriter(new OutputStreamWriter(conn.getOutputStream(), "UTF-8"));
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 获取请求返回数据（设置返回数据编码为UTF-8）
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream(), "UTF-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
            JSONObject object = JSON.parseObject(result);
            System.out.println(jsonObject);
        } catch (IOException e) {
            e.printStackTrace();
        } finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }

        return jsonObject;
    }

    public static JSONObject doGetJson(String urlstr, String param) {
        JSONObject resultObject = null;
        try {
            URL url = new URL(urlstr); // 创建url //处理base64参数gg问题
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection(); // 得到连接
            urlConnection.setDoOutput(true);
            urlConnection.setDoInput(true);
            urlConnection.setRequestMethod("POST");
            urlConnection.setUseCaches(false);
            OutputStreamWriter out = new OutputStreamWriter(
                    urlConnection.getOutputStream(),"utf-8");
            out.write(param);
            out.flush();
            out.close();
            BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "utf-8"));
            StringBuffer buffer = new StringBuffer();// 存储服务器返回的信息;<span style="font-family: Arial, Helvetica,
            // sans-serif;"></span>
            String line = "";
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            String result = buffer.toString();
            System.out.println("执行dogetjson并返回的数据：" + result);
            resultObject = JSONObject.parseObject(result); // 将服务器返回的字符串转换成json格式
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        }
        return resultObject;
    }

    public static String getBase64(StrutsUploadedFile strutsUploadedFile) {
        InputStream inputStream = null;
        byte [] data = null;
        try{
            inputStream = new FileInputStream(strutsUploadedFile.getContent());
            data = new byte[inputStream.available()];
            inputStream.read(data);
            inputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(data);
    }

    public static int saveToImgByInputStream(InputStream inputStream, String imgPath, String imgName) {
        int status = 0;
        if (inputStream != null && !"".equals(inputStream)) {
            try {
                File dir = new File(imgPath);
                if ( !dir.isFile() ){
                    dir.mkdirs();
                }
                File file = new File(imgPath, imgName);
                if ( !file.exists() ) {
                    file.createNewFile();
                }
                FileOutputStream fos = new FileOutputStream(file);
                byte[] b = new byte[1];
                while (inputStream.read(b) != -1) {
                    fos.write(b);
                }
                fos.flush();
                inputStream.close();
                fos.close();

            } catch (FileNotFoundException e) {
                e.printStackTrace();
                return -1;
            } catch (IOException e) {
                e.printStackTrace();
                return -1;
            }
        }
        return status;
    }

    public OrderDao getDao() {
        return dao;
    }



    @Resource
    public void setDao(OrderDao dao) {
        this.dao = dao;
    }

    public UserDao getUserDao() {
        return userDao;
    }

    @Resource
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public StrutsUploadedFile getOrderImage() {
        return orderImage;
    }

    public void setOrderImage(StrutsUploadedFile orderImage) {
        this.orderImage = orderImage;
    }

    public String getOrderImageContentType() {
        return orderImageContentType;
    }

    public void setOrderImageContentType(String orderImageContentType) {
        this.orderImageContentType = orderImageContentType;
    }

    public String getOrderImageFileName() {
        return orderImageFileName;
    }

    public void setOrderImageFileName(String orderImageFileName) {
        this.orderImageFileName = orderImageFileName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDetailAddress() {
        return detailAddress;
    }

    public void setDetailAddress(String detailAddress) {
        this.detailAddress = detailAddress;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getImgNum() {
        return imgNum;
    }

    public void setImgNum(String imgNum) {
        this.imgNum = imgNum;
    }
}
