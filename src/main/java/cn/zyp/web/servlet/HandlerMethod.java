package cn.zyp.web.servlet;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.util.ClassUtils;

import java.lang.reflect.Method;

public class HandlerMethod {
    private Object bean;
    private Class<?>  beanType;
    private Method method;
    private BeanFactory beanFactory;


    public HandlerMethod(Object bean, Method method) {
        this.bean = bean;
        this.beanType = ClassUtils.getUserClass(bean);
        this.method = method;
        this.beanFactory = null;
    }

    public HandlerMethod(Object bean, Method method, BeanFactory beanFactory) {
        this.bean = bean;
        this.beanType = ClassUtils.getUserClass(bean);
        this.method = method;
        this.beanFactory = beanFactory;
    }

    public Object getBean() {
        return bean;
    }

    public void setBean(Object bean) {
        this.bean = bean;
    }

    public Class<?> getBeanType() {
        return beanType;
    }

    public void setBeanType(Class<?> beanType) {
        this.beanType = beanType;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public BeanFactory getBeanFactory() {
        return beanFactory;
    }

    public void setBeanFactory(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }
}
