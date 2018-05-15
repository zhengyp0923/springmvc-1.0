package cn.zyp.web.servlet.handlerMapping;

import cn.zyp.web.servlet.HandlerExecution;
import cn.zyp.web.servlet.MappedInterceptor;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationObjectSupport;
import org.springframework.web.util.UrlPathHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractHandlerMapping extends WebApplicationObjectSupport implements HandlerMapping {

    //所有的MappedInterceptor
    private List<MappedInterceptor> interceptors = new ArrayList<MappedInterceptor>();
    private UrlPathHelper urlPathHelper = new UrlPathHelper();

    @Override
    protected void initApplicationContext(ApplicationContext context) {
        super.initApplicationContext(context);
        detectedInterceptors(this.interceptors);
    }


    /**
     * 封装HandlerExecution
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public HandlerExecution getHandler(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Object handler = getHandlerInternal(request);

        HandlerExecution handlerExecution = new HandlerExecution();
        handlerExecution.setHandler(handler);

        //封装拦截器
        String lookupPath = urlPathHelper.getLookupPathForRequest(request);
        for (MappedInterceptor interceptor : this.interceptors) {
            if (interceptor.match(lookupPath)) {
                handlerExecution.getInterceptors().add(interceptor);
            }
        }
        return handlerExecution;
    }

    /**
     * 获取handler
     *
     * @param request
     * @return
     */
    protected abstract Object getHandlerInternal(HttpServletRequest request);

    //获取所有的拦截器
    private void detectedInterceptors(List<MappedInterceptor> interceptors) {
        //从spring容器中获取MappedHandlerInterceptor
        interceptors.addAll(BeanFactoryUtils.beansOfTypeIncludingAncestors(getApplicationContext(), MappedInterceptor.class, true, false).values());
    }

    public List<MappedInterceptor> getInterceptors() {
        return interceptors;
    }

    public UrlPathHelper getUrlPathHelper() {
        return urlPathHelper;
    }
}
