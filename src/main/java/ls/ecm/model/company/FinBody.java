package ls.ecm.model.company;

import lombok.Data;
import ls.ecm.model.vo.ProfileVO;

import java.util.List;

@Data
public class FinBody {
    private List<CompanyVO> relatedCompanyVOList;
    private ProfileVO profile;
}
