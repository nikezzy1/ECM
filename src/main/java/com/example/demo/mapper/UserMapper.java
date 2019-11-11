//package com.example.demo.mapper;
//
//import com.example.demo.model.db.UserRecord;
//import org.apache.ibatis.annotations.*;
////import org.apache.ibatis.type.EnumOrdinalTypeHandler;
//
//public interface UserMapper {
//
//    UserRecord getOneByPhone(@Param("phone") String phone,
//                             @Param("channelId") String channelId);
//
////    UserRecord getOneByIdCardNumber(@Param("idCardNumber") String idCardNumber,
////                                    @Param("channelId") String channelId);
////
////    UserRecord getOneById(@Param("id") Long id);
//
//    Integer insertOne(@Param("phone") String phone,
//                      @Param("channelId") String channelId,
//                      @Param("password") String password,
//                      @Param("salt") String salt,
//                      @Param("auditFlowRole") String auditFlowRole);
////
////    Integer insertAuthorizedPerson(@Param("name") String name,
////                                   @Param("idCardNumber") String idCardNumber,
////                                   @Param("phone") String phone,
////                                   @Param("status") int status,
////                                   @Param("channelId") String channelId);
////
////    Integer updateIdCardInfo(@Param("naturalPersonId") Long naturalPersonId,
////                             @Param("name") String name,
////                             @Param("idCardNumber") String idCardNumber);
////
////
////    Integer updateBankId(@Param("naturalPersonId") Long naturalPersonId,
////                         @Param("bankId") Long bankId);
////
////
////    Integer updateCFCAUserId(@Param("naturalPersonId") Long naturalPersonId,
////                             @Param("cfcaUserId") String cfcaUserId);
//
//
//    Integer updateLoginPassword(@Param("naturalPersonId") Long naturalPersonId,
//                                @Param("password") String password);
//
//
////    Integer setTransactionPassword(@Param("naturalPersonId") Long naturalPersonId,
////                                   @Param("password") String password);
//
////    Integer updateNaturalPersonStatus(
////            @Param("naturalPersonId") Long naturalPersonId,
////            @Param("status") int status
////    );
//}
