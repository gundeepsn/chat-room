package com.example.chartroom.exception_handler;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = {java.lang.Exception.class})
    public ModelAndView handleAllExceptions(Exception ex){
        System.err.println(ex);
        ModelAndView mv=new ModelAndView();
        mv.setViewName("login");
        return mv;
    }
}
