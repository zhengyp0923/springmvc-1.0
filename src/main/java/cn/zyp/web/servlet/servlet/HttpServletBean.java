package cn.zyp.web.servlet.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

/**
 * 进行Servlet的初始化工作
 */
public abstract class HttpServletBean extends HttpServlet {
    @Override
    public void init() throws ServletException {
        super.init();
    }
}
