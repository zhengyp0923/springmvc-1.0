package cn.zyp.web.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 拦截器
 */
public interface HandlerInterceptor {

    boolean preHandler(HttpServletRequest request, HttpServletResponse response,Object handler)throws Exception;

    void   postHandler(HttpServletRequest request,HttpServletResponse response,ModelAndView modelAndView)throws Exception;

    void afterComplemetion(HttpServletRequest request,HttpServletResponse response,Exception ex)throws Exception;

}
