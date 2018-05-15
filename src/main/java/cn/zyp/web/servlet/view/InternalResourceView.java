package cn.zyp.web.servlet.view;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public class InternalResourceView extends AbstractUrlBasedView {
    private boolean include;

    public InternalResourceView(String url) {
        super(url);
    }

    protected void prepareResponse(HttpServletRequest request, HttpServletResponse response, Map<String, Object> map) throws Exception {

        for (Map.Entry<String, Object> entry : map.entrySet()) {
            request.setAttribute(entry.getKey(), entry.getValue());
        }
        //判断是包含 还是 转发
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(getUrl());
        if (include) {
            requestDispatcher.include(request, response);
        } else {
            requestDispatcher.forward(request, response);
        }
    }
}
