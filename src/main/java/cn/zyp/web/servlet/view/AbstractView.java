package cn.zyp.web.servlet.view;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.LinkedHashMap;
import java.util.Map;

public abstract class AbstractView implements View {

    public void render(HttpServletRequest request, HttpServletResponse response, Map<String, ?> model) throws Exception {
        // 封装模型
        Map<String, Object> map = modelInternal(request, model);
        //响应输出
        prepareResponse(request,response, map);
    }

    protected abstract void prepareResponse(HttpServletRequest request,HttpServletResponse response, Map<String, Object> map)throws Exception;

    private Map<String, Object> modelInternal(HttpServletRequest request, Map<String, ?> model) {
        //TODO 封装模型
        Map<String, Object> map = new LinkedHashMap<String, Object>();
        map.putAll(model);
        return map;
    }
}
