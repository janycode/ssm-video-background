package com.demo.controller;

import com.demo.pojo.User;
import com.demo.service.UserService;
import com.demo.utils.MailUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 查询所有
     * @return
     */
    @RequestMapping("/findAll")
    @ResponseBody
    public List<User> findAll() {
        return userService.findAll();
    }

    /**
     * 登陆
     * @param user
     * @param session
     * @return
     */
    @RequestMapping("/loginUser")
    @ResponseBody
    public String loginUser(User user, HttpSession session) {
        //System.out.println(user);
        User existUser = userService.login(user);
        if (null != existUser) {
            session.setAttribute("userAccount", existUser.getEmail());
            return "success";
        }
        return "error";
    }

    /**
     * 退出登陆
     * @param session
     * @return
     */
    @RequestMapping(path = {"/loginOut", "/loginOut2"})
    public String loginOut(HttpSession session) {
        //System.out.println("loginOut");
        session.invalidate();
        return "/before/index.jsp";
    }

    /**
     * 注册
     * @param user
     * @return
     */
    @RequestMapping("/insertUser")
    @ResponseBody
    public String insertUser(User user, HttpSession session) {
        System.out.println("注册：" + user);
        // 因注册成功会自动登陆上，故需要设置 session 记录登陆状态
        if (userService.insertUser(user) == 1) {
            session.setAttribute("userAccount", user.getEmail());
            return "success";
        }
        return "error";
    }

    /**
     * 邮箱校验
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
     * @return
     */
    @RequestMapping("/forgetPassword")
    public String forgetPassword() {
        return "/before/forget_password.jsp";
    }

    /**
     * 用户输入的邮箱验证码进行比较
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

}
