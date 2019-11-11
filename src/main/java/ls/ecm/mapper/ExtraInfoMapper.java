package ls.ecm.mapper;

import ls.ecm.model.ExtraInfo;
import ls.ecm.model.enums.AlivenessDetectStatus;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.EnumOrdinalTypeHandler;


public interface ExtraInfoMapper {
    @Select("select * from natural_person_extra_info where natural_person_id = #{naturalPersonId} limit 1")
    @Results({
            @Result(property = "alivenessDetect", column = "aliveness_detect",
                    typeHandler = EnumOrdinalTypeHandler.class, javaType = AlivenessDetectStatus.class),
            @Result(property = "naturalPersonId", column = "natural_person_id"),
            @Result(property = "status", column = "status"),
            @Result(property = "gender", column = "gender"),
            @Result(property = "birthDate", column = "birth_date"),
            @Result(property = "address", column = "address"),
            @Result(property = "authority", column = "authority"),
            @Result(property = "validDate", column = "valid_date"),
            @Result(property = "portraitImagePath", column = "portrait_image_path"),
            @Result(property = "emblemImagePath", column = "emblem_image_path")

    })
    ExtraInfo getByNaturalPersonId(@Param("naturalPersonId") long naturalPersonId);


    @Insert("insert into natural_person_extra_info (natural_person_id, gender, birth_date, address, authority, " +
            "valid_date, id_card_image_path) values (#{naturalPersonId}," +
            " #{gender}, #{birthDate}, #{address}, #{authority}, #{validDate}, #{idCardImagePath})")
    Integer addNaturalPersonExtraInfo(
            @Param("naturalPersonId") long naturalPersonId,
            @Param("gender") String gender,
            @Param("birthDate") String birthDate,
            @Param("address") String address,
            @Param("authority") String authority,
            @Param("validDate") String validDate);

    @Update("update natural_person_extra_info set gender = #{gender}, birth_date = #{birthDate}, address = #{address}, " +
            " authority = #{authority}, valid_date = #{validDate} where natural_person_id  = #{naturalPersonId}")
    Integer update(
            @Param("naturalPersonId") long naturalPersonId,
            @Param("gender") String gender,
            @Param("birthDate") String birthDate,
            @Param("address") String address,
            @Param("authority") String authority,
            @Param("validDate") String validDate);

    @Update("update natural_person_extra_info set aliveness_detect = #{alivenessDetect} where natural_person_id = #{naturalPersonId}")
    Integer updateAlivenessDetect(
            @Param("naturalPersonId") long naturalPersonId,
            @Param("alivenessDetect") int alivenessDetect
    );

    @Update("update natural_person_extra_info set stand_hold_image_url = #{standHoldImageURL}, aliveness_detect = #{alivenessDetect} where natural_person_id = #{naturalPersonId}")
    Integer updateStandAlivenessDetect(
            @Param("naturalPersonId") long naturalPersonId,
            @Param("standHoldImageURL") String standHoldImageURL,
            @Param("alivenessDetect") int alivenessDetect
    );

    @Update("update natural_person_extra_info set emblem_image_path = #{emblem} where natural_person_id = #{naturalPersonId}")
    Integer updateEmblem(
            @Param("naturalPersonId") long naturalPersonId,
            @Param("emblem") String emblem
    );

    @Update("update natural_person_extra_info set portrait_image_path = #{portrait} where natural_person_id = #{naturalPersonId}")
    Integer updatePortrait(
            @Param("naturalPersonId") long naturalPersonId,
            @Param("portrait") String portrait
    );

    @Insert("insert into natural_person_extra_info (natural_person_id) values (#{naturalPersonId})")
    Integer initExtraInfo(
            @Param("naturalPersonId") long naturalPersonId
    );
}
