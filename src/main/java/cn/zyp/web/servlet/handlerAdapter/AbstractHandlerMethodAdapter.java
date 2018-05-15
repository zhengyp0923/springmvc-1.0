package cn.zyp.web.servlet.handlerAdapter;

import cn.zyp.web.servlet.HandlerMethod;
import cn.zyp.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class AbstractHandlerMethodAdapter implements HandlerAdapter {

    public boolean support(Object handler) throws Exception {
        return (handler instanceof HandlerMethod) && supportInternal((HandlerMethod) handler);
    }

    public ModelAndView handler(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return handlerInternal(request, response, (HandlerMethod)handler);
    }

    public long lastModified() throws Exception {
        return lastModifiedInternal();
    }
    protected abstract long lastModifiedInternal()throws Exception;
    protected abstract ModelAndView handlerInternal(HttpServletRequest request, HttpServletResponse response, HandlerMethod handler)throws Exception;
    protected abstract boolean supportInternal(HandlerMethod handler)throws Exception;
}
