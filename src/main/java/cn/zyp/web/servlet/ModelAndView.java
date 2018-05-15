package cn.zyp.web.servlet;

import java.util.HashMap;
import java.util.Map;

public class ModelAndView {
    private Map<String, Object> model = new HashMap<String, Object>();
    private Object view;


    public ModelAndView() {
    }

    public ModelAndView(Map<String, Object> model, Object view) {
        this.model = model;
        this.view = view;
    }

    public Map<String, Object> getModel() {
        return model;
    }

    public void setModel(Map<String, Object> model) {
        this.model = model;
    }

    public Object getView() {
        return view;
    }

    public void setView(Object view) {
        this.view = view;
    }
}
