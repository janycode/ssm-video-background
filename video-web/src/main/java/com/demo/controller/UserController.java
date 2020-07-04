package com.demo.controller;

import com.demo.pojo.User;
import com.demo.service.UserService;
import com.demo.utils.MailUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    // 测试防止重放攻击，记录时间戳
    private long userRegTime = 0;
    // 测试防止重放攻击，记录 IP 地址
    private String userRegIP = null;

    /**
     * 查询所有
     *
     * @return
     */
    @RequestMapping("/findAll")
    @ResponseBody
    public List<User> findAll() {
        return userService.findAll();
    }

    /**
     * 登陆
     *
     * @param user
     * @param session
     * @return
     */
    @RequestMapping("/loginUser")
    @ResponseBody
    public String loginUser(User user, HttpSession session, HttpServletRequest request) {
        System.out.println(getIP(request) + " 正在 login:" + user);
        User existUser = userService.login(user);
        if (null != existUser) {
            session.setAttribute("userAccount", existUser.getEmail());
            return "success";
        }
        return "error";
    }

    /**
     * 退出登陆
     *
     * @param session
     * @return
     */
    @RequestMapping(path = {"/loginOut", "/loginOut2"})
    public String loginOut(HttpSession session) throws IOException {
        session.invalidate();
        return "redirect:/user/toIndex"; // 不拼地址前缀
    }

    @RequestMapping("/toIndex")
    public String toIndex() {
        return "/before/index.jsp"; // 拼前缀
    }

    /**
     * 获取IP
     *
     * @param request
     * @return
     */
    public static String getIP(HttpServletRequest request) {
        String ip = request.getRemoteAddr();
        String headerIP = request.getHeader("x-real-ip");
        if (headerIP == null || "".equals(headerIP) || "null".equals(headerIP)) {
            headerIP = request.getHeader("x-forwarded-for");
        }
        if (headerIP != null && !"".equals(headerIP) && !"null".equals(headerIP)) {
            ip = headerIP;
        }
        System.out.println("ip:" + ip);
        return ip;
    }

    /**
     * 注册
     *
     * @param user
     * @return
     */
    @RequestMapping("/insertUser")
    @ResponseBody
    public String insertUser(User user, HttpSession session, HttpServletRequest request) {
        System.out.println("注册：" + user);

        // 时间戳测试防止重放攻击
        String ip = getIP(request);
        System.out.println("注册ip=" + ip);
        // 第一次进来注册时将 IP 记录上
        if (userRegIP == null) {
            userRegIP = ip;
        }
        // 非第一次注册时，对比上次的IP：相同 IP 限制注册时间为 30s，不同 IP 暂不做限制(若限制，逻辑相同)
        if (userRegIP.equals(ip)){
            // 第一次进来注册时将 时间戳(s) 记录上
            if (userRegTime == 0) {
                userRegTime = new Date().getTime();
            } else {
                // 非第一次注册时，对比上次记录的时间
                long currentTime = new Date().getTime();
                System.out.println("userRegTime=" + userRegTime + ", currentTime=" + currentTime);
                if (currentTime - userRegTime < 30 * 1000) { // 限制300s，单位：毫秒
                    userRegTime = currentTime;
                    System.out.println("-----------------------------------------恶意注册：返回 error!!!");
                    return "error";
                }
            }
        }

        // 因注册成功会自动登陆上，故需要设置 session 记录登陆状态
        if (userService.insertUser(user) == 1) {
            session.setAttribute("userAccount", user.getEmail());
            System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@正常注册：返回 success!!!");
            return "success";
        }
        return "error";
    }

    /**
     * 邮箱校验
     *
     * @param email
     * @return
     */
    @RequestMapping("/validateEmail")
    @ResponseBody
    public String validateEmail(String email) {
        System.out.println("邮箱校验：" + email);
        return userService.validateEmail(email) == null ? "success" : "error";
    }


    /**
     * 通过登陆后的域中的账号，查询账户信息，跳转个人资料显示
     *
     * @param session
     * @return
     */
    @RequestMapping("/showMyProfile")
    public ModelAndView showMyProfile(HttpSession session) {
        //System.out.println("showMyProfile");

        ModelAndView mav = new ModelAndView();
        String userAccount = (String) session.getAttribute("userAccount");
        User user = userService.queryUserByEmail(userAccount);

        mav.addObject("user", user);
        mav.setViewName("/before/my_profile.jsp");
        return mav;
    }

    /**
     * 跳转忘记密码的修改页面
     *
     * @return
     */
    @RequestMapping("/forgetPassword")
    public String forgetPassword() {
        return "/before/forget_password.jsp";
    }

    /**
     * 用户输入的邮箱验证码进行比较
     *
     * @param email
     * @param code
     * @param session
     * @return
     */
    @RequestMapping("/validateEmailCode")
    public String validateEmailCode(String email, String code, HttpSession session) {
        System.out.println("输入邮箱：" + email + ", 输入验证码：" + code);
        String userAccount = (String) session.getAttribute("userAccount");
        String randomCode = (String) session.getAttribute("randomCode");

        // 如果域中没有邮箱，那么将现在输入的存域，用于在重置密码时查询该账户信息
        if (userAccount == null) {
            session.setAttribute("userAccount", email);
        }

        // 确保输入的邮箱没有被修改，以及验证码输入的与生成存入域中的相同，跳转重置密码页面
        if (code.equals(randomCode)) {
            return "/before/reset_password.jsp";
        }
        return "/before/forget_password.jsp";
    }

    /**
     * 发送邮件（ajax 异步请求处理发送邮件验证码）
     *
     * @param email
     * @param session
     * @return
     */
    @RequestMapping("/sendEmail")
    @ResponseBody
    public String sendEmail(String email, HttpSession session) {
        // 该邮箱的用户存在
        User existUser = userService.validateEmail(email);
        if (existUser == null) {
            return "hasNoUser"; // 响应到 ajax，无此用户
        }
        // 有该用户时，生成随机码并发送邮件
        String randomCode = MailUtils.generateRandomCode(5);
        System.out.println("生成的邮箱验证码：" + randomCode);
        session.setAttribute("randomCode", randomCode);
        boolean result = MailUtils.sendMail(email, randomCode);
        // 邮件发送完成，响应
        return result ? "success" : "error";
    }

    /**
     * 重置密码，先查询再设置，更新数据库
     *
     * @param password
     * @param session
     * @return
     */
    @RequestMapping("/resetPassword")
    public String resetPassword(String password, HttpSession session) {
        System.out.println("新密码：" + password);

        String userAccount = (String) session.getAttribute("userAccount");
        boolean flag = false;
        if (userAccount != null) {
            // 获取用户信息，set 密码，再更新回数据库
            User user = userService.queryUserByEmail(userAccount);
            user.setPassword(password);
            flag = userService.updatePassword(user);
        }

        return flag ? "/before/index.jsp" : "/before/reset_password.jsp";
    }

    /**
     * 取 邮箱，跳转修改页
     * @param session
     * @return
     */
    @RequestMapping("/changeProfile")
    public String changeProfile(HttpSession session) {
        String userAccount = (String) session.getAttribute("userAccount");
        User user = userService.queryUserByEmail(userAccount);
        session.setAttribute("user", user);
        return "/before/change_profile.jsp";
    }

    /**
     * 修改用户基本信息
     * @param user
     * @param session
     * @return
     */
    @RequestMapping("/updateUser")
    public String updateUser(User user, HttpSession session) {
        System.out.println("user:" + user);
        // 将域中存储的 email 即账号存储进设置的对象中，用于查找-更新
        User existUser = (User) session.getAttribute("user");
        user.setEmail(existUser.getEmail());
        int result = userService.updateUserByEmail(user);
        return "/before/my_profile.jsp";
    }

    /**
     * 取 邮箱，跳转更改图片页
     * @param session
     * @return
     */
    @RequestMapping("/changeAvatar")
    public String changeAvatar(HttpSession session) {
        String userAccount = (String) session.getAttribute("userAccount");
        User user = userService.queryUserByEmail(userAccount);
        session.setAttribute("user", user);
        return "/before/change_avatar.jsp";
    }

    @RequestMapping("/upLoadImage")
    public String upLoadImage() {


        return "/before/my_profile.jsp";
    }
}
