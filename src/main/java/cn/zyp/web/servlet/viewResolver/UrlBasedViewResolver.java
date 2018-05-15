package cn.zyp.web.servlet.viewResolver;

import cn.zyp.web.servlet.view.InternalResourceView;
import cn.zyp.web.servlet.view.RedirectView;
import cn.zyp.web.servlet.view.View;

public class UrlBasedViewResolver extends AbstractCachingViewResolver {
    private static final String REDIRECT_PREFIX = "redirect:";
    private static final String FORWARD_PREFIX = "forward:";

    /**
     * 创建View
     * @param viewName
     * @return
     */
    protected View createView(String viewName) {
        View view = null;
        //重定向
        if (viewName.startsWith(REDIRECT_PREFIX)) {
            viewName = viewName.substring(REDIRECT_PREFIX.length());
            view = new RedirectView(viewName);
            return view;
        }
        //转发
        if (viewName.startsWith(FORWARD_PREFIX)) {
            viewName = viewName.substring(FORWARD_PREFIX.length());
            view = new InternalResourceView(viewName);
            return view;
        }
        return null;
    }
}
