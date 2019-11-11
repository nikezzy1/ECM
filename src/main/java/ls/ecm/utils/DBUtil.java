package ls.ecm.utils;


import ls.ecm.exception.ErrorCode;
import ls.ecm.exception.ServiceException;

public class DBUtil {
    public static void checkDBResult(Integer result) throws ServiceException {
        if (result == 0) {
            throw new ServiceException(ErrorCode.DBException);
        }
    }
}
