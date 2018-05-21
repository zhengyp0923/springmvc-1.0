package cn.zyp.web.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

public class HandlerExecution {
    private Object handler;
    private List<HandlerInterceptor> interceptors = new ArrayList<HandlerInterceptor>();
    private int interceptorIndex = -1;

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

    public boolean applyPreHandler(HttpServletRequest request, HttpServletResponse response) throws Exception {
        if(this.interceptors.size()>0){
            for (int i = 0; i < this.interceptors.size(); i++) {
                if (!interceptors.get(i).preHandler(request, response, handler)) {
                    triggerAfterCompletion(request, response, null);
                    return false;
                }
                interceptorIndex = i;
            }
        }
        return true;
    }

    public void applyPostHandler(HttpServletRequest request, HttpServletResponse response, ModelAndView mv) throws Exception {
       if(this.interceptors.size()>0){
           for (int i = this.interceptors.size() - 1; i >= 0; i--) {
               interceptors.get(i).postHandler(request, response, mv);
           }
       }
    }

    public void triggerAfterCompletion(HttpServletRequest request, HttpServletResponse response, Exception ex) throws Exception {
        if (this.interceptors.size() > 0) {
            for (int i = this.interceptorIndex; i >= 0; i--) {
                interceptors.get(i).afterComplemetion(request, response, ex);
            }
        }
    }
}
