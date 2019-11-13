package ls.ecm.mapper;


import ls.ecm.model.TestUserInfo;
import org.apache.ibatis.annotations.*;

public interface TestUserInfoMapper {

    @Select("select * from user_info where id = #{id}")
    @Results(value = {
            @Result(property = "id", column = "id" ),
            @Result(property = "userName", column = "userName" ),
            @Result(property = "password", column = "password" )
    })
    TestUserInfo selectById(@Param("id") Integer id);
}
