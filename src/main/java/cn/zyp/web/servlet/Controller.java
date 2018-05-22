package cn.zyp.web.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * 
 */
public interface Controller {

    ModelAndView handler(HttpServletRequest request, HttpServletResponse response);
}
