package ls.ecm.mapper;

import ls.ecm.model.db.UserRecord;
import ls.ecm.model.enums.AuditFlowRole;
import ls.ecm.model.enums.NaturalPersonStatus;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.EnumOrdinalTypeHandler;

public interface UserMapper {

        @Select("select * from natural_person where phone = #{phone} and channel_id = #{channelId} limit 1")
        @Results(value = {
                @Result(property = "name", column = "name"),
                @Result(property = "phone", column = "phone"),
                @Result(property = "title", column = "title"),
                @Result(property = "idCardNumber", column = "id_card_number"),
                @Result(property = "channelId", column = "channel_id"),
                @Result(property = "status", column = "status", typeHandler = EnumOrdinalTypeHandler.class, javaType = NaturalPersonStatus.class),
                @Result(property = "auditFlowRole", column = "audit_flow_role", typeHandler = EnumOrdinalTypeHandler.class, javaType = AuditFlowRole.class),
                @Result(property = "cfcaUserId", column = "cfca_user_id"),
                @Result(property = "selfPhone", column = "self_phone")
        })
        UserRecord getOneByPhone(@Param("phone") String phone,
                                 @Param("channelId") String channelId);


        @Select("select * from natural_person where id_card_number = #{idCardNumber} limit 1")
        @Results({
                @Result(property = "name", column = "name"),
                @Result(property = "phone", column = "phone"),
                @Result(property = "title", column = "title"),
                @Result(property = "idCardNumber", column = "id_card_number"),
                @Result(property = "channelId", column = "channel_id"),
                @Result(property = "status", column = "status", typeHandler = EnumOrdinalTypeHandler.class, javaType = NaturalPersonStatus.class),
                @Result(property = "auditFlowRole", column = "audit_flow_role", typeHandler = EnumOrdinalTypeHandler.class, javaType = AuditFlowRole.class),
                @Result(property = "cfcaUserId", column = "cfca_user_id"),
                @Result(property = "selfPhone", column = "self_phone")
        })
        UserRecord getOneByIdCardNumber(@Param("idCardNumber") String idCardNumber,
                                        @Param("channelId") String channelId);

        @Select("select * from natural_person where id = #{id} limit 1")
        @Results({
                @Result(property = "name", column = "name"),
                @Result(property = "phone", column = "phone"),
                @Result(property = "title", column = "title"),
                @Result(property = "idCardNumber", column = "id_card_number"),
                @Result(property = "channelId", column = "channel_id"),
                @Result(property = "status", column = "status", typeHandler = EnumOrdinalTypeHandler.class, javaType = NaturalPersonStatus.class),
                @Result(property = "auditFlowRole", column = "audit_flow_role", typeHandler = EnumOrdinalTypeHandler.class, javaType = AuditFlowRole.class),
                @Result(property = "cfcaUserId", column = "cfca_user_id"),
                @Result(property = "selfPhone", column = "self_phone")
        })
        UserRecord getOneById(@Param("id") Long id);

        @Insert("insert into natural_person (phone, password, salt, audit_flow_role, channel_id) values " +
                "(#{phone}, #{password}, #{salt}, #{auditFlowRole}, #{channelId})")
        Integer insertOne(@Param("phone") String phone,
                          @Param("channelId") String channelId,
                          @Param("password") String password,
                          @Param("salt") String salt,
                          @Param("auditFlowRole") String auditFlowRole);

        @Insert("insert into natural_person (name, id_card_number, phone, status, channel_id) values " +
                "(#{name}, #{idCardNumber}, #{phone}, #{status}, #{channelId} )")
        Integer insertAuthorizedPerson(@Param("name") String name,
                                       @Param("idCardNumber") String idCardNumber,
                                       @Param("phone") String phone,
                                       @Param("status") int status,
                                       @Param("channelId") String channelId);

        @Update("update natural_person set name = #{name}, id_card_number = #{idCardNumber} " +
                "where id = #{naturalPersonId}")
        Integer updateIdCardInfo(@Param("naturalPersonId") Long naturalPersonId,
                                 @Param("name") String name,
                                 @Param("idCardNumber") String idCardNumber);


        @Update("update natural_person set cfca_user_id = #{cfcaUserId} where id = #{naturalPersonId}")
        Integer updateCFCAUserId(@Param("naturalPersonId") Long naturalPersonId,
                                 @Param("cfcaUserId") String cfcaUserId);

        @Update("update natural_person set password = #{password} where id = #{naturalPersonId}")
        Integer updateLoginPassword(@Param("naturalPersonId") Long naturalPersonId,
                                    @Param("password") String password);

        @Update("update natural_person set audit_flow_role = #{auditFlowRole} where id = #{naturalPersonId}")
        Integer updateAuditFlowRole(
                @Param("naturalPersonId") long naturalPersonId,
                @Param("auditFlowRole") String auditFlowRole);


        @Update("update natural_person set status = #{status} where id = #{naturalPersonId}")
        Integer updateNaturalPersonStatus(
                @Param("naturalPersonId") Long naturalPersonId,
                @Param("status") int status
        );
    }
