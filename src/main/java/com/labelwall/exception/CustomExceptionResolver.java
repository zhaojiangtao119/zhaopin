package com.labelwall.exception;

import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Administrator on 2017-12-30.
 */
public class CustomExceptionResolver implements HandlerExceptionResolver {

    /**
     * @param request
     * @param response
     * @param handler
     * @param ex       系统抛出的异常，在这里进行截获，进行异常处理
     * @return
     */
    @Override
    public ModelAndView resolveException(HttpServletRequest request,
                                         HttpServletResponse response,
                                         Object handler, Exception ex) {
        //handler就是处理器适配器要执行的handler对象（只有method）
        CustomException customException = null;
        //instanceof解析异常类型
        if (ex instanceof CustomException) {
            customException = (CustomException) ex;
        }else{
            customException = new CustomException("未知错误");
        }
        String message = customException.getMessage();

        ModelAndView mv = new ModelAndView("../page/error");
        mv.addObject("message",message);
        return mv;
    }
}
