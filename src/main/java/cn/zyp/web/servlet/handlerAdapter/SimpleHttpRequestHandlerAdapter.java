package cn.zyp.web.servlet.handlerAdapter;

import cn.zyp.web.servlet.ModelAndView;
import org.springframework.web.HttpRequestHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * handler 类型是HttpRequestHandler
 */
public class SimpleHttpRequestHandlerAdapter implements HandlerAdapter {
    public boolean support(Object handler) throws Exception {
        return handler instanceof HttpRequestHandler;
    }

    public ModelAndView handler(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        ((HttpRequestHandler) handler).handleRequest(request, response);
        return null;
    }

    public long lastModified() throws Exception {
        return -1;
    }
}
