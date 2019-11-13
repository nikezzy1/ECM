package ls.ecm.dao;

import ls.ecm.exception.ErrorCode;
import ls.ecm.exception.ServiceException;
import ls.ecm.mapper.CompanyMapper;
import ls.ecm.mapper.ExtraInfoMapper;
import ls.ecm.mapper.PrivilegeMapper;
import ls.ecm.mapper.UserMapper;
import ls.ecm.model.company.CompanyDetailReq;
import ls.ecm.model.db.CompanyRecord;
import ls.ecm.utils.DBUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class CompanyDao {

    @Autowired
    private CompanyMapper companyMapper;

    @Transactional
    public CompanyRecord addCompany(CompanyDetailReq companyDetailReq) throws ServiceException {
        CompanyRecord companyRecord = new CompanyRecord();
        BeanUtils.copyProperties(companyDetailReq, companyRecord);

        Optional.ofNullable(companyMapper.getCompanyByLicenseNo(companyRecord.getLicenseNo(), companyRecord.getChannelId()))
                .ifPresent((s) -> {
                    throw new ServiceException(ErrorCode.CompanyExistsException);
                });

        Integer result = companyMapper.insertOne(companyRecord);
        DBUtil.checkDBResult(result);
        return companyRecord;
    }

}
