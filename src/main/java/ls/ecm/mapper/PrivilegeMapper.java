package ls.ecm.mapper;

import ls.ecm.model.Privilege;
import ls.ecm.model.Rights;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.EnumOrdinalTypeHandler;

import java.util.List;

public interface PrivilegeMapper {
    @Update("update privilege set rights = #{rights}, title = #{title} where natural_person_id = #{naturalPersonId} and object_id = #{objectId} ")
    Integer updatePrivilege(
            @Param("naturalPersonId") long naturalPersonId,
            @Param("objectId") long objectId,
            @Param("title") String title,
            @Param("rights") int rights);

    @Insert("insert into privilege (natural_person_id, object_id, title, rights)" +
            " values (#{naturalPersonId}, #{objectId}, #{title}, " +
            "#{rights})")
    Integer insertOne(@Param("naturalPersonId") long naturalPersonId,
                      @Param("objectId") long objectId,
                      @Param("title") String title,
                      @Param("rights") Integer rights);

    @Update("update privilege set rights = #{rights} where natural_person_id = #{naturalPersonId} " +
            "and object_id = #{objectId}")
    Integer update(
            @Param("naturalPersonId") long naturalPersonId,
            @Param("objectId") long objectId,
            @Param("rights") int rights);

    @Insert("update privilege set rights = #{rights} where natural_person_id = #{naturalPersonId} and object_id = #{objectId} ")
    Integer cancelAuthorizationPerson(
            @Param("naturalPersonId") long naturalPersonId,
            @Param("objectId") long objectId,
            @Param("rights") Integer rights
    );


    @Select("select * from privilege where natural_person_id = #{naturalPersonId}")
    @Results({
            @Result(property = "naturalPersonId", column = "natural_person_id"),
            @Result(property = "objectId", column = "object_id"),
            @Result(property = "rights", column = "rights", typeHandler = EnumOrdinalTypeHandler.class, javaType = Rights.class),
            @Result(property = "privilegeStatus", column = "status"),
    })
    List<Privilege> getByNaturalPersonId(@Param("naturalPersonId") long naturalPersonId);


    @Select("select * from privilege where natural_person_id = #{naturalPersonId} and object_id = #{objectId}" +
            " limit 1")
    @Results({
            @Result(property = "naturalPersonId", column = "natural_person_id"),
            @Result(property = "objectId", column = "object_id"),
            @Result(property = "rights", column = "rights", typeHandler = EnumOrdinalTypeHandler.class, javaType = Rights.class),
            @Result(property = "privilegeStatus", column = "status"),
    })
    Privilege getByNaturalPersonIdAndobjectId(@Param("naturalPersonId") long naturalPersonId,
                                              @Param("objectId") long objectId);

    @Select("select * from privilege where object_id = #{objectId}")
    @Results({
            @Result(property = "naturalPersonId", column = "natural_person_id"),
            @Result(property = "objectId", column = "object_id"),
            @Result(property = "rights", column = "rights", typeHandler = EnumOrdinalTypeHandler.class, javaType = Rights.class),
            @Result(property = "title", column = "title"),
            @Result(property = "privilegeStatus", column = "status"),
    })
    List<Privilege> getPrivilegeByObjectId(@Param("objectId") long objectId);
}
