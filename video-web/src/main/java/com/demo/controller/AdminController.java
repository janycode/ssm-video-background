package com.demo.controller;

import com.demo.pojo.Admin;
import com.demo.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @RequestMapping("/toLogin")
    public String toLogin() {
        return "/behind/login.jsp";
    }

    /**
     * 操作登录
     * @param admin
     * @return
     */
    @RequestMapping("/login")
    @ResponseBody
    public String login(Admin admin, HttpSession session) {
        Admin loginAdmin = adminService.login(admin);
        if (loginAdmin != null) {
            session.setAttribute("loginAdmin", loginAdmin);
            return "success";
        }
        return "error";
    }

    /**
     * 退出登录
     * @param session
     * @throws IOException
     */
    @RequestMapping("/exit")
    public String exit(HttpSession session) throws IOException {
        session.invalidate();
        return "/behind/login.jsp";
    }

}
