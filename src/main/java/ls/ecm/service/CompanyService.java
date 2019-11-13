package ls.ecm.service;

import ls.ecm.exception.ServiceException;
import ls.ecm.model.company.CompanyBasicInfo;
import ls.ecm.model.company.CompanyDetailReq;
import ls.ecm.model.company.CompanyRelatedInfo;
import ls.ecm.model.db.CompanyRecord;


public interface CompanyService {
    /**
     *  企业四要素检验
     * @param organizationName
     * @param orgIdentificationNumber
     * @param name
     * @param identificationNumber
     * @throws ServiceException
     */
    void checkCompanyFactors(String organizationName, String orgIdentificationNumber, String name,
                             String identificationNumber) throws ServiceException;

    /**
     * 用户绑定公司（需要企业四要素）
     * @param naturalPersonId
     * @param companyDetailReq
     * @return
     * @throws ServiceException
     */
    CompanyRecord addCompany(long naturalPersonId, CompanyDetailReq companyDetailReq) throws ServiceException;

    /**
     * 用户绑定公司（不需要企业四要素）
     * @param naturalPersonId
     * @param companyDetailReq
     * @return
     * @throws ServiceException
     */
    CompanyRecord addCompanyWhitoutFactor(long naturalPersonId, CompanyDetailReq companyDetailReq) throws ServiceException;


}
