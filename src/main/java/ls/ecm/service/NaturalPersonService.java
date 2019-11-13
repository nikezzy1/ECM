package ls.ecm.service;

        import ls.ecm.exception.ServiceException;
        import ls.ecm.model.company.CompanyVO;
        import ls.ecm.model.req.*;
        import ls.ecm.model.vo.ProfileVO;
        import ls.ecm.model.vo.UploadEmblemVO;
        import ls.ecm.model.vo.UploadPortraitVO;
        import ls.ecm.model.vo.UserVO;
        import org.apache.ibatis.annotations.Param;

        import java.util.List;


public interface NaturalPersonService {


    /**
     * 注册
     * @param registerReq
     * @return
     * @throws ServiceException
     */
    UserVO register(RegisterReq registerReq) throws ServiceException;

    /**
     * 登陆
     * @param signInReq
     * @return
     * @throws ServiceException
     */
    UserVO signIn(SignInReq signInReq) throws ServiceException;

    /**
     * 获取个人相关信息
     * @param
     * @return
     * @throws ServiceException
     */
    ProfileVO getProfile() throws ServiceException;
    ProfileVO getProfile(long naturalPersonId) throws ServiceException;

    /**
     * 提供身份证人像面信息
     * @param naturalPersonId
     * @param uploadPortraitReq
     * @return
     * @throws ServiceException
     */
    UploadPortraitVO uploadPortrait(long naturalPersonId, UploadPortraitReq uploadPortraitReq) throws ServiceException;

    /**
     * 提供身份证国徽面信息
     * @param naturalPersonId
     * @param uploadEmblemReq
     * @return
     * @throws ServiceException
     */
    UploadEmblemVO uploadEmblem(long naturalPersonId, UploadEmblemReq uploadEmblemReq) throws ServiceException;

    /**
     * 核对身份证信息是否被注册使用
     * @param naturalPersonId
     * @param card
     * @throws ServiceException
     */
    void confirmIdCardInfo(long naturalPersonId,WechatCardReq card) throws ServiceException;

    /**
     * 获取关联公司
     * @param naturalPersonId
     * @return
     * @throws ServiceException
     */
    List<CompanyVO> getRelatedCompanies(long naturalPersonId) throws ServiceException;

    /**
     * 设置登陆密码
     * @param naturalPersonId
     * @param password
     * @throws ServiceException
     */
    void setLoginPassword(long naturalPersonId, String password) throws ServiceException;

    /**
     * 忘记登陆密码
     * @param naturalPersonId
     * @param passwordReq
     * @throws ServiceException
     */
    void setForgetLoginPassword(long naturalPersonId, PasswordReq passwordReq) throws ServiceException;

    /**
     * 设置获取登陆人的角色
     * @param naturalPersonId
     * @param auditFlowRole
     * @throws ServiceException
     */
    void setAuditFlowRole(long naturalPersonId, int auditFlowRole) throws ServiceException;


}
