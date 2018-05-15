package cn.zyp.web.servlet.handlerMapping;

import cn.zyp.web.servlet.RequestMappingInfo;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.reflect.Method;

public class RequestMappingHandlerMapping extends AbstractHandlerMethodMapping {
    @Override
    public void afterPropertiesSet() throws Exception {
        super.afterPropertiesSet();
    }

    protected RequestMappingInfo getMappingForMethod(Class<?> beanType, Method method) {
        RequestMappingInfo requestMappingInfo = new RequestMappingInfo();
        RequestMapping requestMapping = AnnotatedElementUtils.findMergedAnnotation(method, RequestMapping.class);

        requestMappingInfo.setMethods(requestMapping.method());
        requestMappingInfo.setPatterns(requestMapping.value());
        requestMappingInfo.setProduces(requestMapping.produces());
        requestMappingInfo.setConsumer(requestMapping.consumes());
        return requestMappingInfo;
    }
}
