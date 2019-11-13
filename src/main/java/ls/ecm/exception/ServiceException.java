package ls.ecm.exception;

import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;

/**
 * @Description: 业务类异常
 * @author zzy
 * @date 2019/10/23 15:30
 *
 */
public class ServiceException extends RuntimeException implements Serializable{
    private int code;
    private String msg;

    //private static final long serialVersionUID = 1213855733833039552L;

    public ServiceException() {
    }

    public ServiceException(ErrorCode errorCode) {
        this.msg = errorCode.getDescription();
        this.code = errorCode.getCode();
    }

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceException(int code, String msg) {
        this.msg = msg;
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}