package ls.ecm.dao;

import ls.ecm.exception.ServiceException;
import ls.ecm.mapper.ExtraInfoMapper;
import ls.ecm.model.ExtraInfo;
import ls.ecm.model.enums.AlivenessDetectStatus;
import ls.ecm.model.req.WechatCardReq;
import ls.ecm.utils.DBUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class ExtraInfoDao {
    @Autowired
    private ExtraInfoMapper extraInfoMapper;

    public ExtraInfo getById(long naturalPersonId) {
        ExtraInfo extraInfo = extraInfoMapper.getByNaturalPersonId(naturalPersonId);
        if (extraInfo == null) {
            return new ExtraInfo();
        }
        return extraInfo;
    }

    @Transactional
    public void updateIdCardInfo(long naturalPersonId, WechatCardReq card) throws ServiceException {
        ExtraInfo extraInfo = extraInfoMapper.getByNaturalPersonId(naturalPersonId);
        if (extraInfo == null) {
            Integer result = extraInfoMapper.addNaturalPersonExtraInfo(naturalPersonId,
                    card.getGender(),
                    card.getBirthDate(),
                    card.getAddress(),
                    card.getAuthority(),
                    card.getValidDate());
            DBUtil.checkDBResult(result);
        } else {
            BeanUtils.copyProperties(card, extraInfo);
            updateIdCard(extraInfo);
        }
    }

    @Transactional
    public void updateEmblem(long naturalPersonId, String emblem) throws ServiceException {
        Integer result = extraInfoMapper.updateEmblem(naturalPersonId, emblem);
        DBUtil.checkDBResult(result);
    }

    @Transactional
    public void updatePortrait(long naturalPersonId, String portrait) throws ServiceException {
        Integer result = extraInfoMapper.updatePortrait(naturalPersonId, portrait);
        DBUtil.checkDBResult(result);
    }

    @Transactional
    public void updateIdCard(ExtraInfo extraInfo) throws ServiceException {
        Integer result = extraInfoMapper.update(extraInfo.getNaturalPersonId(),
                extraInfo.getGender(),
                extraInfo.getBirthDate(),
                extraInfo.getAddress(),
                extraInfo.getAuthority(),
                extraInfo.getValidDate());
        DBUtil.checkDBResult(result);
    }

    @Transactional
    public void updateAlivenessDetect(long naturalPersonId, AlivenessDetectStatus alivenessDetectStatus) throws ServiceException {
        Integer result = extraInfoMapper.updateAlivenessDetect(naturalPersonId, alivenessDetectStatus.getCode());
        DBUtil.checkDBResult(result);
    }

    @Transactional
    public void updateAlivenessDetect(long naturalPersonId, String standHoldImageURL, AlivenessDetectStatus alivenessDetectStatus) throws ServiceException {
        Integer result = extraInfoMapper.updateStandAlivenessDetect(naturalPersonId, standHoldImageURL, alivenessDetectStatus.getCode());
        DBUtil.checkDBResult(result);
    }


    @Transactional
    public void initNaturalPersonExtraInfo(long naturalPersonId) throws ServiceException {
        Integer result = extraInfoMapper.initExtraInfo(naturalPersonId);
        DBUtil.checkDBResult(result);
    }
}
