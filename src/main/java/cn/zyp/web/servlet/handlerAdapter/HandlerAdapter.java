package cn.zyp.web.servlet.handlerAdapter;

import cn.zyp.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface HandlerAdapter {

    /**
     * 是否支持handler
     *
     * @param handler
     * @return
     * @throws Exception
     */
    boolean support(Object handler) throws Exception;

    /**
     * 执行handler
     *
     * @param request
     * @param response
     * @param handler
     * @return
     */
    ModelAndView handler(HttpServletRequest request, HttpServletResponse response, Object handler)throws Exception;

    /**
     * 最后的修改时间
     *
     * @return
     */
    long lastModified()throws Exception;
}
