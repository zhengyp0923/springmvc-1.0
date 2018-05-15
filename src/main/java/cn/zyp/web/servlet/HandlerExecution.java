package cn.zyp.web.servlet;

import java.util.ArrayList;
import java.util.List;

public class HandlerExecution {
    private Object handler;
    private List<HandlerInterceptor> interceptors = new ArrayList<HandlerInterceptor>();

    public HandlerExecution() {
    }

    public HandlerExecution(Object handler, List<HandlerInterceptor> interceptors) {
        this.handler = handler;
        this.interceptors = interceptors;
    }

    public Object getHandler() {
        return handler;
    }

    public void setHandler(Object handler) {
        this.handler = handler;
    }

    public List<HandlerInterceptor> getInterceptors() {
        return interceptors;
    }

    public void setInterceptors(List<HandlerInterceptor> interceptors) {
        this.interceptors = interceptors;
    }
}
