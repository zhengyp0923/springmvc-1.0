package cn.zyp.web.servlet.handlerAdapter;

import cn.zyp.web.servlet.ModelAndView;

import javax.servlet.Servlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * handler是servlet
 */
public class SimpleServletHandlerAdapter implements HandlerAdapter {
    public boolean support(Object handler) throws Exception {
        return handler instanceof Servlet;
    }

    public ModelAndView handler(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        ((Servlet) handler).service(request, response);
        return null;
    }

    public long lastModified() throws Exception {
        return -1;
    }
}
