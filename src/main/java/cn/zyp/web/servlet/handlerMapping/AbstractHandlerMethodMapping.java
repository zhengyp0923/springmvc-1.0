package cn.zyp.web.servlet.handlerMapping;

import cn.zyp.web.servlet.HandlerMethod;
import cn.zyp.web.servlet.RequestMappingInfo;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.MethodIntrospector;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.util.ClassUtils;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Map;

public abstract class AbstractHandlerMethodMapping extends AbstractHandlerMapping implements InitializingBean {

    private final Map<RequestMappingInfo, HandlerMethod> urlMapping = new LinkedHashMap<RequestMappingInfo, HandlerMethod>();
    private final Map<String, RequestMappingInfo> urlLookup = new LinkedHashMap<String, RequestMappingInfo>();


    protected Object getHandlerInternal(HttpServletRequest request) {
        String lookupPath = getUrlPathHelper().getLookupPathForRequest(request);
        RequestMappingInfo requestMappingInfo = urlLookup.get(lookupPath);
        HandlerMethod handlerMethod = urlMapping.get(requestMappingInfo);
        return handlerMethod;
    }

    public void afterPropertiesSet() throws Exception {
        Map<String, Object> objectMap = BeanFactoryUtils.beansOfTypeIncludingAncestors(getApplicationContext(), Object.class, true, false);
        for (Map.Entry<String, Object> entry : objectMap.entrySet()) {
            Object bean = entry.getValue();
            final Class<?> beanType = ClassUtils.getUserClass(bean);

            if (AnnotatedElementUtils.hasAnnotation(beanType, Controller.class)) {
                //将所有的Method的注解封装
                Map<Method, RequestMappingInfo> methods = MethodIntrospector.selectMethods(beanType, new MethodIntrospector.MetadataLookup<RequestMappingInfo>() {
                    @Nullable
                    public RequestMappingInfo inspect(Method method) {
                        return getMappingForMethod(beanType, method);
                    }
                });

                //注册到Map中
                for (Map.Entry<Method, RequestMappingInfo> MREntry : methods.entrySet()) {
                    Method invocableMethod = AopUtils.selectInvocableMethod(MREntry.getKey(), beanType);
                    RequestMappingInfo requestMappingInfo = MREntry.getValue();

                    HandlerMethod handlerMethod = new HandlerMethod(bean, invocableMethod);
                    urlMapping.put(requestMappingInfo, handlerMethod);

                    String[] patterns = requestMappingInfo.getPatterns();
                    for (String pattern:patterns){
                        urlLookup.put(pattern,requestMappingInfo);
                    }

                }
            }
        }
    }

    protected abstract RequestMappingInfo getMappingForMethod(Class<?> beanType, Method method);

    public Map<RequestMappingInfo, HandlerMethod> getUrlMapping() {
        return urlMapping;
    }

    public Map<String, RequestMappingInfo> getUrlLookup() {
        return urlLookup;
    }
}
