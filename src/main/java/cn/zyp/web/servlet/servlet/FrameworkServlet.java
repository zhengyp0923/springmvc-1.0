package cn.zyp.web.servlet.servlet;

import org.springframework.context.ApplicationContextAware;
import org.springframework.core.NamedThreadLocal;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public abstract class FrameworkServlet  extends HttpServletBean implements ApplicationContextAware{

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    @Override
    protected void doHead(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    @Override
    protected void doTrace(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    //TODO 不明白
//    private class ContextRefreshListener implements ApplicationListener<ContextRefreshedEvent> {
//
//        public void onApplicationEvent(ContextRefreshedEvent event) {
//            FrameworkServlet.this.onApplicationEvent(event);
//        }
//    }
//
//    public void onApplicationEvent(ContextRefreshedEvent event) {
//        onRefresh(event.getApplicationContext());
//    }
//
//    protected void onRefresh(ApplicationContext applicationContext) {
//        //子类进行初始化
//    }


    public void processRequest(HttpServletRequest request, HttpServletResponse response){
           ThreadLocal threadLocal=new NamedThreadLocal("dispather");
            doRequest(request,response);
    }

    protected abstract void doRequest(HttpServletRequest request, HttpServletResponse response);
}
