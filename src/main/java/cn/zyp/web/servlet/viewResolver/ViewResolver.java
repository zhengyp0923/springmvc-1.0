package cn.zyp.web.servlet.viewResolver;

import cn.zyp.web.servlet.view.View;

public interface ViewResolver  {

    View resolver(String viewName)throws Exception;
}
