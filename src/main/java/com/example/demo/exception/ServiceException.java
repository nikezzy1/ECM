package com.example.demo.exception;

import java.io.Serializable;

/**
 * @Description: 业务类异常
 * @author zzy
 * @date 2019/10/23 15:30
 *
 */
public class ServiceException extends RuntimeException implements Serializable{

    private static final long serialVersionUID = 1213855733833039552L;

    public ServiceException() {
    }

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}