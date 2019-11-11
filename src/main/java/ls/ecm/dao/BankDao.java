//package ls.ecm.dao;
//
//import ls.ecm.exception.ServiceException;
//import ls.ecm.model.enums.BankStatus;
//import ls.ecm.model.req.BankRecord;
//import ls.ecm.model.req.BankReq;
//import org.springframework.beans.BeanUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.Optional;
//
//@Component
//public class BankDao {
//    @Autowired
//    private BankMapper bankMapper;
//    @Autowired
//    private NaturalPersonDao naturalPersonDao;
//    @Autowired
//    private PrivilegeDao privilegeDao;
//
//    public BankRecord getBankById(long bankId) {
//        return Optional.ofNullable(bankMapper.getById(bankId)).orElse(new BankRecord());
//    }
//
//    @Transactional
//    public BankRecord companyBindBank(long naturalPersonId, BankReq bankReq) throws ServiceException {
//        return bindBank(naturalPersonId, bankReq, BankStatus.PUBLIC_COMPANY_CARD);
//    }
//
//    public BankRecord bindBank(long naturalPersonId, BankReq bankReq, BankStatus bankStatus) throws ServiceException {
//        BankRecord bankRecord = new BankRecord();
//        BeanUtils.copyProperties(bankReq, bankRecord);
//        bankRecord.setNaturalPersonId(naturalPersonId);
//        bankRecord.setBankStatus(bankStatus);
//
//        Integer result = bankMapper.insertOne(bankRecord);
//        DBUtil.checkDBResult(result);
//        return bankRecord;
//    }
//
//
//    @Transactional
//    public BankRecord privateBindBank(long naturalPersonId, BankReq bankReq) throws ServiceException {
//        BankRecord bankRecord = bindBank(naturalPersonId, bankReq, BankStatus.PRIVATE_COMPANY_CARD);
//
//        naturalPersonDao.updateBankId(naturalPersonId, bankRecord.getId());
//        bankReq.setId(bankRecord.getId());
//        // 个人增加对私银行卡，表示授信完成。增加一条权限记录，companyId为0，权限为自然人。
//        privilegeDao.addPrivilege(naturalPersonId, 0l, "", Rights.NATURAL_PERSON);
//        return bankRecord;
//    }
//}
