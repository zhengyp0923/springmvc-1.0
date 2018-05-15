package cn.zyp.web.servlet.view;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 视图的渲染
 */
public interface View {
    void render(HttpServletRequest request, HttpServletResponse response, Map<String, ?> model) throws Exception;
}
