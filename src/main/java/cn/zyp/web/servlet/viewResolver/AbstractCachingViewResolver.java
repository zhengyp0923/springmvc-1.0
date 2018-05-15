package cn.zyp.web.servlet.viewResolver;

import cn.zyp.web.servlet.view.View;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract  class AbstractCachingViewResolver implements ViewResolver{
    private  int size;
    //是否开启缓存
    private boolean on;

    public AbstractCachingViewResolver() {
        this.size=1024;
        this.on=true;
    }

    public AbstractCachingViewResolver(int size) {
        this.size = size;
        this.on=true;
    }

    public AbstractCachingViewResolver(int size, boolean on) {
        this.size = size;
        this.on = on;
    }

    private  volatile  Map<String,View> viewAccess=new ConcurrentHashMap<String, View>();

    //LRU缓存维护key
    private final Map<String,String> viewKeys=new LinkedHashMap<String, String>(size,0.75f,true){
        @Override
        protected boolean removeEldestEntry(Map.Entry<String, String> eldest) {
            if(size()>size){
                viewAccess.remove(eldest.getKey());
                return true;
            }
            return false;
        }
    };

    /**
     * 判断缓存中是否有
     * 如果没有 双检锁保证唯一性
     * @param viewName
     * @return
     * @throws Exception
     */
    public View resolver(String viewName) throws Exception {
        View view=null;
        if(!on){
            view=createView(viewName);
        }else {
            if(viewKeys.get(viewName)!=null){
                //缓存中有
                view=viewAccess.get(viewName);
            }else {
                //缓存中没有  创建
               if(viewKeys.get(viewName)==null){
                       synchronized (viewAccess){
                           if(viewKeys.get(viewName)==null){
                               viewAccess.put(viewName,createView(viewName));
                               viewKeys.put(viewName,viewName);
                           }
                       }
                }
                   view=viewAccess.get(viewName);
            }
        }
        return view;
    }

    /**
     * 创建view
     * @param viewName
     * @return
     */
    protected abstract View createView(String viewName);

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public Map<String, View> getViewAccess() {
        return viewAccess;
    }

    public Map<String, String> getViewKeys() {
        return viewKeys;
    }

    public boolean isOn() {
        return on;
    }

    public void setOn(boolean on) {
        this.on = on;
    }
}
