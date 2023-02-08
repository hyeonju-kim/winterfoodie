package com.winterfoodies.ceo.exception;


import org.springframework.http.HttpMethod;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;

public class ExceptionUtils {
    public static boolean isForView(HttpServletRequest request){
        return request.getMethod().toString().equals(HttpMethod.GET.toString());
    }

}
