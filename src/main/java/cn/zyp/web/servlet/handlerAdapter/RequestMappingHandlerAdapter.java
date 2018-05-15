package cn.zyp.web.servlet.handlerAdapter;

import cn.zyp.web.servlet.HandlerMethod;
import cn.zyp.web.servlet.ModelAndView;
import javassist.*;
import javassist.bytecode.CodeAttribute;
import javassist.bytecode.LocalVariableAttribute;
import javassist.bytecode.MethodInfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.LinkedHashMap;
import java.util.Map;

public class RequestMappingHandlerAdapter extends AbstractHandlerMethodAdapter {

    private boolean synchorizedOnSession;

    public RequestMappingHandlerAdapter() {
    }

    public RequestMappingHandlerAdapter(boolean synchorizedOnSession) {
        this.synchorizedOnSession = synchorizedOnSession;
    }

    protected boolean supportInternal(HandlerMethod handler)throws Exception {
        return true;
    }

    protected ModelAndView handlerInternal(HttpServletRequest request, HttpServletResponse response, HandlerMethod handler)throws Exception {
        ModelAndView modelAndView = null;
        Object view = null;

        //session同步调用
        if (synchorizedOnSession){
            //参数封装
           synchronized (request.getSession(true)){
               view = methodInvoke(request, response, handler);
           }
        }else {
            view = methodInvoke(request, response, handler);
        }
        modelAndView = new ModelAndView();
        modelAndView.setView(view);
        return modelAndView;
    }


    protected long lastModifiedInternal()throws Exception {
        return -1;
    }

    /**
     * 执行方法
     *
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    private Object methodInvoke(HttpServletRequest request, HttpServletResponse response, HandlerMethod handler)
            throws Exception {
        Object view = null;

        Method method = handler.getMethod();
        Object target = handler.getBean();

        // 获取Method的参数名称
        String[] parameters = getParameters(target, method);
        // 进行参数的封装
        Map<String, Object> map = paramters(request,response,method, parameters);

        // 赋值并反射调用方法
        view = method.invoke(target, map.values().toArray());

        return view;

    }

    /**
     * 进行参数的赋值
     *
     * @param request
     * @return
     */
    private Map<String, Object> paramters(HttpServletRequest request, HttpServletResponse response, Method method,
                                          String[] parameters) {
        Map<String, Object> result = new LinkedHashMap<String, Object>();

        // 基本的参数赋值
        for (String paramter : parameters) {
            result.put(paramter, request.getParameter(paramter));
        }

        // 处理HttpServletRequest HttpServletResponse HttpSession类型的参数
        Class<?>[] parameterTypes = method.getParameterTypes();

        HttpSession session = request.getSession();

        for (int i = 0; i < parameterTypes.length; i++) {
            if (parameterTypes[i] == HttpServletRequest.class) {
                result.put(parameters[i], request);
            }

            if (parameterTypes[i] == HttpServletResponse.class) {
                result.put(parameters[i], response);
            }

            if (parameterTypes[i] == HttpSession.class) {
                result.put(parameters[i], session);
            }
        }

        return result;
    }

    /**
     * javasist获取方法参数的名称
     *
     * @param target
     * @param method
     * @return
     */
    private String[] getParameters(Object target, Method method) {
        String[] paramNames = null;

        try {
            ClassPool pool = ClassPool.getDefault();
            ClassClassPath classPath = new ClassClassPath(this.getClass());
            pool.insertClassPath(classPath);

            CtClass cc = pool.get(target.getClass().getName());
            CtMethod cm = cc.getDeclaredMethod(method.getName());

            // 使用javaassist的反射方法获取方法的参数名
            MethodInfo methodInfo = cm.getMethodInfo();
            CodeAttribute codeAttribute = methodInfo.getCodeAttribute();
            LocalVariableAttribute attr = (LocalVariableAttribute) codeAttribute
                    .getAttribute(LocalVariableAttribute.tag);
            if (attr == null) {
                // exception
            }
            paramNames = new String[cm.getParameterTypes().length];
            int pos = Modifier.isStatic(cm.getModifiers()) ? 0 : 1;
            for (int i = 0; i < paramNames.length; i++)
                paramNames[i] = attr.variableName(i + pos);
            // paramNames即参数名
            for (int i = 0; i < paramNames.length; i++) {
                System.out.println(paramNames[i]);
            }

        } catch (NotFoundException e) {
            e.printStackTrace();
        }
        return paramNames;
    }

    public boolean isSynchorizedOnSession() {
        return synchorizedOnSession;
    }

    public void setSynchorizedOnSession(boolean synchorizedOnSession) {
        this.synchorizedOnSession = synchorizedOnSession;
    }
}
