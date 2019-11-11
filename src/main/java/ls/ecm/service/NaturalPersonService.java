package ls.ecm.service;

        import ls.ecm.exception.ServiceException;
        import ls.ecm.model.req.RegisterReq;
        import ls.ecm.model.req.SignInReq;
        import ls.ecm.model.vo.ProfileVO;
        import ls.ecm.model.vo.UserVO;


public interface NaturalPersonService {


    UserVO register(RegisterReq registerReq) throws ServiceException;

    UserVO signIn(SignInReq signInReq) throws ServiceException;

    ProfileVO getProfile(String token) throws ServiceException;

}
