package cn.zyp.web.servlet.handlerMapping;

import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.util.UrlPathHelper;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedHashMap;
import java.util.Map;

public class SimplrUrlHandlerMapping extends AbstractHandlerMapping {
    private Map<String, Object> urls = new LinkedHashMap<String, Object>();

    private PathMatcher pathMatcher = new AntPathMatcher();
    private UrlPathHelper urlPathHelper = new UrlPathHelper();

    /**
     * 寻找handler
     *
     * @return
     */
    protected Object getHandlerInternal(HttpServletRequest request) {
        String lookupPath = urlPathHelper.getLookupPathForRequest(request);
        Object handler = this.urls.get(lookupPath);

        if (handler instanceof String) {
            handler = getApplicationContext().getBean((String) handler);
        }
        //TODO 模糊匹配
        return handler;
    }

    public Map<String, Object> getUrls() {
        return urls;
    }

    public void setUrls(Map<String, Object> urls) {
        this.urls = urls;
    }

    public PathMatcher getPathMatcher() {
        return pathMatcher;
    }

    @Override
    public UrlPathHelper getUrlPathHelper() {
        return urlPathHelper;
    }
}
