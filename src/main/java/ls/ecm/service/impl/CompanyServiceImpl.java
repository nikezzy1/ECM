package ls.ecm.service.impl;

import com.lingsi.platform.ecm.service.CFCAAuthService;
import com.lingsi.platform.ecm.service.impl.CFCAAuthServiceImpl;
import lombok.extern.slf4j.Slf4j;
import ls.ecm.dao.CompanyDao;
import ls.ecm.dao.NaturalPersonDao;
import ls.ecm.dao.PrivilegeDao;
import ls.ecm.exception.ErrorCode;
import ls.ecm.exception.ServiceException;
import ls.ecm.model.Rights;
import ls.ecm.model.company.CompanyDetailReq;
import ls.ecm.model.db.CompanyRecord;
import ls.ecm.model.db.UserRecord;
import ls.ecm.service.CompanyService;
import ls.ecm.utils.ExceptionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CompanyServiceImpl implements CompanyService {
    @Autowired
    private CompanyService companyService;

    @Autowired
    private NaturalPersonDao naturalPersonDao;

    @Autowired
    private CompanyDao companyDao;

    @Autowired
    private PrivilegeDao privilegeDao;

    CFCAAuthService cfcaAuthService = new CFCAAuthServiceImpl();

    @Override
    public void checkCompanyFactors(String organizationName, String orgIdentificationNumber, String name,
                                    String identificationNumber) throws ServiceException {

        try {

            Boolean checkResult=cfcaAuthService.authCompany(organizationName,orgIdentificationNumber,name,identificationNumber);
            if (!checkResult) {
                log.info("四要素校验失败");
                throw new ServiceException(ErrorCode.CFCAException.getCode(),"四要素校验失败");
            }
        } catch (Exception e) {
           e.getStackTrace();
            log.error("企业四要素校验异常: "+"堆栈信息："+ExceptionUtil.getStackTrace(e));
            throw new ServiceException(ErrorCode.CFCAException);
        }
    }


    @Override
    public CompanyRecord addCompany(long naturalPersonId, CompanyDetailReq companyDetailReq) throws ServiceException {
        UserRecord userRecord = naturalPersonDao.getById(naturalPersonId);
        try {
            Boolean checkResult=cfcaAuthService.authCompany(companyDetailReq.getCompanyName(), companyDetailReq.getLicenseNo(),
                    userRecord.getName(), userRecord.getIdCardNumber());
            if (!checkResult) {
                log.info("四要素校验失败");
                throw new ServiceException(ErrorCode.CFCAException.getCode(),"四要素校验失败");
            }

            CompanyRecord companyRecord = companyDao.addCompany(companyDetailReq);
            if (companyDetailReq.getCompanyType().equals("public")) {
                privilegeDao.addPrivilege(naturalPersonId, companyRecord.getId(), Rights.PUBLIC_COMPANY_LEGAL_PERSON.getDescription(), Rights.PUBLIC_COMPANY_LEGAL_PERSON);
            } else {
                privilegeDao.addPrivilege(naturalPersonId, companyRecord.getId(), Rights.PRE_PRIVATE_COMPANY_LEGAL_PERSON.getDescription(), Rights.PRE_PRIVATE_COMPANY_LEGAL_PERSON);
            }
            return companyRecord;
        }catch (Exception e) {
            e.getStackTrace();
            throw new ServiceException("绑定企业异常");
        }
    }

    @Override
    public CompanyRecord addCompanyWhitoutFactor(long naturalPersonId, CompanyDetailReq companyDetailReq) throws ServiceException {
        try {
                CompanyRecord companyRecord = companyDao.addCompany(companyDetailReq);
                if (companyDetailReq.getCompanyType().equals("public")) {
                    privilegeDao.addPrivilege(naturalPersonId, companyRecord.getId(), Rights.PUBLIC_COMPANY_LEGAL_PERSON.getDescription(), Rights.PUBLIC_COMPANY_LEGAL_PERSON);
                } else {
                    privilegeDao.addPrivilege(naturalPersonId, companyRecord.getId(), Rights.PRE_PRIVATE_COMPANY_LEGAL_PERSON.getDescription(), Rights.PRE_PRIVATE_COMPANY_LEGAL_PERSON);
                }
                return companyRecord;
        }catch (Exception e) {
            e.getStackTrace();
            throw new ServiceException("绑定企业异常");
        }
    }

}
