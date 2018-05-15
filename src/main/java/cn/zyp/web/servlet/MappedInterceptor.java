package cn.zyp.web.servlet;

import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MappedInterceptor implements HandlerInterceptor {

    private HandlerInterceptor interceptor;
    private PathMatcher pathMatcher=new AntPathMatcher();

    private String[] includes;
    private String[] exIncludes;

    public MappedInterceptor() {
    }

    public MappedInterceptor(HandlerInterceptor interceptor, String[] includes, String[] exIncludes) {
        this.interceptor = interceptor;
        this.includes = includes;
        this.exIncludes = exIncludes;
    }

    public boolean preHandler(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return interceptor.preHandler(request, response, handler);
    }

    public void postHandler(HttpServletRequest request, HttpServletResponse response, ModelAndView modelAndView) throws Exception {
        interceptor.preHandler(request, response, modelAndView);
    }

    public void afterComplemetion(HttpServletRequest request, HttpServletResponse response, Exception ex) throws Exception {
        interceptor.afterComplemetion(request, response, ex);
    }


    /**
     * 判断是否拦截
     * @param lookup  用户的请求路径
     * @return
     */
    public boolean match(String lookup){
        for (String ex:exIncludes){
           if(pathMatcher.match(lookup,ex)){
               return false;
           }
        }

        for (String in:includes){
            if(pathMatcher.match(lookup,in)){
                return true;
            }
        }
        return false;
    }

    public HandlerInterceptor getInterceptor() {
        return interceptor;
    }

    public void setInterceptor(HandlerInterceptor interceptor) {
        this.interceptor = interceptor;
    }



    public String[] getExIncludes() {
        return exIncludes;
    }

    public void setExIncludes(String[] exIncludes) {
        this.exIncludes = exIncludes;
    }



    public String[] getIncludes() {
        return includes;
    }

    public void setIncludes(String[] includes) {
        this.includes = includes;
    }

    public PathMatcher getPathMatcher() {
        return pathMatcher;
    }
}
