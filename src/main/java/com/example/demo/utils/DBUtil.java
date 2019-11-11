package com.example.demo.utils;


import com.example.demo.exception.ErrorCode;
import com.example.demo.exception.ServiceException;

public class DBUtil {
    public static void checkDBResult(Integer result) throws ServiceException {
        if (result == 0) {
            throw new ServiceException(ErrorCode.DBException);
        }
    }
}
