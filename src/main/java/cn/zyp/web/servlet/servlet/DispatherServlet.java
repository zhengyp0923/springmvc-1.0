package cn.zyp.web.servlet.servlet;

import cn.zyp.web.servlet.HandlerExecution;
import cn.zyp.web.servlet.HandlerInterceptor;
import cn.zyp.web.servlet.ModelAndView;
import cn.zyp.web.servlet.exception.ViewNotFoundException;
import cn.zyp.web.servlet.handlerAdapter.HandlerAdapter;
import cn.zyp.web.servlet.handlerMapping.HandlerMapping;
import cn.zyp.web.servlet.view.View;
import cn.zyp.web.servlet.viewResolver.ViewResolver;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 如何进行的初始化   并且进行测试
 */
public class DispatherServlet extends FrameworkServlet {
    private List<HandlerMapping> handlerMappings;
    private List<HandlerAdapter> handlerAdapters;
    private List<ViewResolver> viewResolvers;


    @Override
    public void init() throws ServletException {
        super.init();
        WebApplicationContext applicationContext = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
        initHandlerMappings(applicationContext);
        initHandlerAdapters(applicationContext);
        initViewResolver(applicationContext);
    }

    private void initViewResolver(ApplicationContext applicationContext) {
        Map<String, ViewResolver> matchingBeans =
                BeanFactoryUtils.beansOfTypeIncludingAncestors(applicationContext, ViewResolver.class, true, false);

        if (matchingBeans.isEmpty()) {
            throw new RuntimeException("ViewResolver is Empty");
        }
        this.viewResolvers = new ArrayList<ViewResolver>(matchingBeans.values());
    }

    private void initHandlerAdapters(ApplicationContext applicationContext) {
        Map<String, HandlerAdapter> matchingBeans =
                BeanFactoryUtils.beansOfTypeIncludingAncestors(applicationContext, HandlerAdapter.class, true, false);

        if (matchingBeans.isEmpty()) {
            throw new RuntimeException("HandlerAdapter is Empty");
        }
        this.handlerAdapters = new ArrayList<HandlerAdapter>(matchingBeans.values());
    }

    private void initHandlerMappings(ApplicationContext applicationContext) {
        Map<String, HandlerMapping> matchingBeans =
                BeanFactoryUtils.beansOfTypeIncludingAncestors(applicationContext, HandlerMapping.class, true, false);
        if (matchingBeans.isEmpty()) {
            throw new RuntimeException("HandlerMapping is Empty");
        }
        this.handlerMappings = new ArrayList<HandlerMapping>(matchingBeans.values());
    }

    protected void doRequest(HttpServletRequest request, HttpServletResponse response) {
        //对于包含的处理
        doDispather(request, response);
    }

    private void doDispather(HttpServletRequest request, HttpServletResponse response) {
        HandlerExecution handlerExecution = null;
        //获取Handler
        try {
            handlerExecution = getHandler(request, response);
            if (handlerExecution == null) {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                return;
            }
            List<HandlerInterceptor> interceptors = handlerExecution.getInterceptors();
            Object handler = handlerExecution.getHandler();

            HandlerAdapter handlerAdapter = getHandlerAdapter(handler);
            if (handlerAdapter == null) {
                throw new RuntimeException("HandlerAdapter not support " + handler);
            }

            long lastModified = handlerAdapter.lastModified();
            String method = request.getMethod();
            if ("GET".equals(method) || "HEAD".equals(method)) {
                //TODO 判断是否资源改变
            }

            //执行所有拦截器的 preHandler()
            boolean b = handlerExecution.applyPreHandler(request, response);
            if (!b) {
                return;
            }
            ModelAndView modelAndView = handlerAdapter.handler(request, response, handler);
            handlerExecution.applyPostHandler(request, response, modelAndView);


            View view = getView((String) modelAndView.getView());
            if (view == null) {
                throw new ViewNotFoundException("view ==null");
            }

            view.render(request, response, modelAndView.getModel());
            handlerExecution.triggerAfterCompletion(request, response, null);
        } catch (Exception ex) {
            ex.printStackTrace();
            try {
                handlerExecution.triggerAfterCompletion(request, response, ex);
            } catch (Exception e) {
                e.printStackTrace();
            }
            throw new RuntimeException(ex);
        }
    }

    private View getView(String view) throws Exception {
        for (ViewResolver viewResolver : viewResolvers) {
            View v = viewResolver.resolver(view);
            if (v != null) {
                return v;
            }
        }
        return null;
    }

    private HandlerAdapter getHandlerAdapter(Object handler) throws Exception {
        for (HandlerAdapter handlerAdapter : this.handlerAdapters) {
            if (handlerAdapter.support(handler)) {
                return handlerAdapter;
            }
        }
        return null;
    }

    private HandlerExecution getHandler(HttpServletRequest request, HttpServletResponse response) throws Exception {
        for (HandlerMapping handlerMapping : this.handlerMappings) {
            HandlerExecution handler = handlerMapping.getHandler(request, response);
            if (handler != null) {
                return handler;
            }
        }
        return null;
    }

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

    }
}
