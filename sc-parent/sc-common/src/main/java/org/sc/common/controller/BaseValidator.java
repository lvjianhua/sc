package org.sc.common.controller;

import org.springframework.validation.Validator;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Webb Dong on 2017/3/30.
 */
public abstract class BaseValidator implements Validator {
    private HttpServletRequest request;

    public BaseValidator(HttpServletRequest request) {
        this.request = request;
    }

    public HttpServletRequest getRequest() {
        return request;
    }
}
