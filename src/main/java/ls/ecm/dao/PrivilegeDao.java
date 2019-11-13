package ls.ecm.dao;

import ls.ecm.exception.ServiceException;
import ls.ecm.mapper.CompanyMapper;
import ls.ecm.mapper.PrivilegeMapper;
import ls.ecm.model.Privilege;
import ls.ecm.model.Rights;
import ls.ecm.model.company.CompanyVO;
import ls.ecm.model.db.CompanyRecord;
import ls.ecm.utils.DBUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Component
public class PrivilegeDao {
    @Autowired
    private PrivilegeMapper privilegeMapper;
    @Autowired
    private CompanyMapper companyMapper;


    public List<Privilege> getPrivilegeByNaturalPersonId(long naturalPersonId) {
        return privilegeMapper.getByNaturalPersonId(naturalPersonId);
    }

    @Transactional
    public void updatePrivilege(long naturalPersonId, long companyId, Rights rights) throws ServiceException {
        Integer result = privilegeMapper.updatePrivilege(naturalPersonId, companyId, rights.getDescription(), rights.getCode());
        DBUtil.checkDBResult(result);
    }

    @Transactional
    public void addPrivilege(long naturalPersonId, Long companyId, String title, Rights rights) {
        Privilege privilege = privilegeMapper.getByNaturalPersonIdAndobjectId(naturalPersonId, companyId);
        if (privilege == null) {
            privilegeMapper.insertOne(naturalPersonId, companyId, title, rights.getCode());
        }
    }

    @Transactional
    public void cancelAuthorizationPerson(long naturalPersonId, long companyId, Rights rights) {
        privilegeMapper.cancelAuthorizationPerson(naturalPersonId, companyId, rights.getCode());
    }

    public Privilege getPrivilegeByNaturalPersonIdAndCompanyId(long naturalPersonId, long companyId) {
        return privilegeMapper.getByNaturalPersonIdAndobjectId(naturalPersonId, companyId);
    }

    public List<Privilege> getPrivilegeByCompanyId(long companyId) {
        return privilegeMapper.getPrivilegeByObjectId(companyId);
    }

    @Transactional
    public void confirmAuthorization(long naturalPersonId, long companyId) throws ServiceException {
        Integer result = privilegeMapper.update(naturalPersonId, companyId, Rights.AUTHORIZED_PERSON.getCode());
        DBUtil.checkDBResult(result);
    }

    @Transactional
    public List<CompanyVO> getRelatedCompanies(long naturalPersonId) throws ServiceException {
        List<CompanyVO> result = new ArrayList<>();
        List<Privilege> privileges = privilegeMapper.getByNaturalPersonId(naturalPersonId);
        if (CollectionUtils.isEmpty(privileges)) {
            return result;
        }

        for (Privilege privilege : privileges) {
            if (privilege.getObjectId() == 0) {
                continue;
            }
            CompanyRecord companyRecord = companyMapper.getCompanyById(privilege.getObjectId());
            CompanyVO companyVO = new CompanyVO();
            if (companyRecord == null) {
                continue;
            }
            BeanUtils.copyProperties(companyRecord, companyVO);
            BeanUtils.copyProperties(privilege, companyVO);
            companyVO.setRights(privilege.getRights().getCode());
            result.add(companyVO);
        }
        return result;
    }
}
