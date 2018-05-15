package cn.zyp.web.servlet.view;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 重定向
 */
public class RedirectView extends AbstractUrlBasedView {
    public RedirectView(String url) {
        super(url);
    }

    protected void prepareResponse(HttpServletRequest request, HttpServletResponse response, Map<String, Object> map) throws Exception {
         for(Map.Entry<String,Object> entry:map.entrySet()){
             request.getSession().setAttribute(entry.getKey(),entry.getValue());
         }
         response.sendRedirect(getUrl());
    }
}
