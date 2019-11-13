package ls.ecm.mapper;

import ls.ecm.model.db.CompanyRecord;
import org.apache.ibatis.annotations.*;


public interface CompanyMapper {
    @Select("select * from company where id = #{id}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "companyName", column = "company_name"),
            @Result(property = "licenseNo", column = "license_no"),
            @Result(property = "cfcaUserId", column = "cfca_user_id"),
            @Result(property = "channelId", column = "channel_id"),
    })
    CompanyRecord getCompanyById(@Param("id") long id);

    @Select("select * from company where license_no = #{licenseNo} and channel_id = #{channelId}")
    @Results({

            @Result(property = "id", column = "id"),
            @Result(property = "companyName", column = "company_name"),
            @Result(property = "licenseNo", column = "license_no"),
            @Result(property = "cfcaUserId", column = "cfca_user_id"),
            @Result(property = "channelId", column = "channel_id"),
    })
    CompanyRecord getCompanyByLicenseNo(@Param("licenseNo") String licenseNo, @Param("channelId") String channelId);


    @Insert("insert into company (company_name, license_no, channel_id)" +
            " values (#{companyName}, #{licenseNo}, #{channelId})")
    @Options(useGeneratedKeys=true, keyProperty="id")
    Integer insertOne(CompanyRecord companyRecord);

    @Update("update company set cfca_user_id = #{cfcaUserId} where id = #{companyId}")
    Integer updateCFCAUserId(
            @Param("companyId") long companyId,
            @Param("cfcaUserId") String cfcaUserId);

}

