package cn.zyp.web.servlet;

import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Arrays;

public class RequestMappingInfo {
    private String[] patterns;
    private RequestMethod[] methods;
    private String[] consumer;
    private String[] produces;

    public RequestMappingInfo() {
    }

    public RequestMappingInfo(String[] patterns, RequestMethod[] methods, String[] consumer, String[] produces) {
        this.patterns = patterns;
        this.methods = methods;
        this.consumer = consumer;
        this.produces = produces;
    }

    public RequestMappingInfo(String[] patterns, RequestMethod[] methods) {
        this.patterns = patterns;
        this.methods = methods;
    }

    public String[] getPatterns() {
        return patterns;
    }

    public void setPatterns(String[] patterns) {
        this.patterns = patterns;
    }

    public RequestMethod[] getMethods() {
        return methods;
    }

    public void setMethods(RequestMethod[] methods) {
        this.methods = methods;
    }

    public String[] getConsumer() {
        return consumer;
    }

    public void setConsumer(String[] consumer) {
        this.consumer = consumer;
    }

    public String[] getProduces() {
        return produces;
    }

    public void setProduces(String[] produces) {
        this.produces = produces;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RequestMappingInfo that = (RequestMappingInfo) o;

        // Probably incorrect - comparing Object[] arrays with Arrays.equals
        if (!Arrays.equals(patterns, that.patterns)) return false;
        // Probably incorrect - comparing Object[] arrays with Arrays.equals
        if (!Arrays.equals(methods, that.methods)) return false;
        // Probably incorrect - comparing Object[] arrays with Arrays.equals
        if (!Arrays.equals(consumer, that.consumer)) return false;
        // Probably incorrect - comparing Object[] arrays with Arrays.equals
        return Arrays.equals(produces, that.produces);
    }

    @Override
    public int hashCode() {
        int result = Arrays.hashCode(patterns);
        result = 31 * result + Arrays.hashCode(methods);
        result = 31 * result + Arrays.hashCode(consumer);
        result = 31 * result + Arrays.hashCode(produces);
        return result;
    }
}
