package com.imooc.demo.handler;

import com.sun.deploy.net.HttpRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 统一异常处理类
 *
 * @author Huangfobo
 * @create 2018-10-01 20:44
 **/
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    private Map<String, Object> exceptionHandler(HttpServletRequest request, Exception e){

        Map<String, Object> modelMap = new HashMap<String, Object>();
        modelMap.put("success", false);
        modelMap.put("errMsg", e.getMessage());
        return modelMap;
    }
}
