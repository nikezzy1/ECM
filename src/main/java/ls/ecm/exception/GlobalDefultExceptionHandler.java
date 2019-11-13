package ls.ecm.exception;

import lombok.extern.slf4j.Slf4j;
import ls.ecm.core.response.CommonRes;
import ls.ecm.utils.ExceptionUtil;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 全局异常处理
 * 在Controller中抛出的异常，当没有被catch处理时，GlobalExceptionHandler中定义的处理方法可以起作用
 * 在方法写明注解@ExceptionHandler，并注明其异常类即可
 */
@Slf4j
@ControllerAdvice
public class GlobalDefultExceptionHandler {
    private final static String EXPTION_MSG_KEY = "message";
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public CommonRes defultExcepitonHandler(HttpServletRequest request, Exception e) {

        request.getSession(true).setAttribute(EXPTION_MSG_KEY, e.getMessage());
        log.error("[exception:GlobalExceptionHandler] {} {}", request.getMethod(), request.getRequestURI());
        log.error("[exception:GlobalExceptionHandler]controller class raise exception. e={}", e);

        return CommonRes.http_error("系统异常").fail();
    }
}
