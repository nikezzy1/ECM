package com.example.demo.mapper;


import com.example.demo.model.UserInfo;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.EnumOrdinalTypeHandler;

public interface UserInfoMapper {

//    @Select("select * from user_info where id = #{id}")
//    @Results(value = {
//            @Result(property = "id", column = "id"),
//            @Result(property = "userName", column = "userName"),
//            @Result(property = "password", column = "password")
//    })
    UserInfo selectById(@Param("id") Integer id);
}
