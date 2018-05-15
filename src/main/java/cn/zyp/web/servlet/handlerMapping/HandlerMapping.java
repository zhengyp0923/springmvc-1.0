package cn.zyp.web.servlet.handlerMapping;

import cn.zyp.web.servlet.HandlerExecution;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface HandlerMapping {

    HandlerExecution getHandler(HttpServletRequest request, HttpServletResponse response)throws Exception;

}
