package ls.ecm.mapper;


import ls.ecm.model.UserInfo;
import org.apache.ibatis.annotations.*;

public interface UserInfoMapper {

    @Select("select * from user_info where id = #{id}")
    @Results(value = {
            @Result(property = "id", column = "id" ),
            @Result(property = "userName", column = "userName" ),
            @Result(property = "password", column = "password" )
    })
    UserInfo selectById(@Param("id") Integer id);
}
