package cn.zyp.web.servlet.handlerAdapter;

import cn.zyp.web.servlet.Controller;
import cn.zyp.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * handler是controller
 */
public class SimpleControllerHandlerAdapter implements HandlerAdapter {
    public boolean support(Object handler) throws Exception {
        return handler instanceof Controller;
    }

    public ModelAndView handler(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return ((Controller)handler).handler(request,response);
    }

    public long lastModified() throws Exception {
        return -1;
    }
}
