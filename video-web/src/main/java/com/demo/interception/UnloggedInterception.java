package com.demo.interception;

import com.demo.pojo.Admin;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 类名：UnloggedInterception
 * 功能：未登录资源的访问拦截
 */
public class UnloggedInterception implements HandlerInterceptor {

    /**
     * 在控制器中的方法执行之前进行拦截，未登录时不能访问控制器方法，重定向到登录页面
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Admin loginAdmin = (Admin) request.getSession().getAttribute("loginAdmin");
        //System.out.println("拦截器 loginAdmin：" + loginAdmin);
        if (loginAdmin == null) {
            // 转发到跳转登陆的方法，拦截器配置中需要配置 该资源不拦截
            request.getRequestDispatcher("/admin/toLogin").forward(request, response);
            //System.out.println("拦截器 生效：重定向到登录页！");
        }
        //System.out.println("拦截器 放行：正常访问！");
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        //System.out.println("拦截器 控制器方法执行后，页面跳转前...");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //System.out.println("拦截器 jsp页面执行后...");
    }
}
