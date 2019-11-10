package com.example.demo.exception;

public enum ErrorCode {

    HTTPIOException(1000001, "网络异常"),
    DBException(2000001, "数据更新失败"),
    EmptyRecordException(3000001, "无此记录"),
    TokenExpiredException(300002, "登陆已经失效，请重新登陆"),
    WriteFileException(3000002, "文件写入错误"),
    UserNotFoundException(3000003, "用户未找到"),
    PasswordErrorException(4000001, "密码不对"),
    PasswordNotSetException(4000002, "密码未设置"),
    CompanyExistsException(4000003, "公司已存在"),
    CompanyNotFoundException(4000007, "公司不存在"),
    LegalPersonNotFoundException(4000008, "法人不存在"),
    UnauthorizedException(4000009, "无权操作"),
    ChannelIdNotFoudnException(4000010, "无channelId"),
    IllegalException(4000011, "数据非法"),
    FileNotFoundException(5000001, "文件丢失"),
    EmptyFileNameException(5000002, "文件异常"),
    CFCAException(5000003, "企业信息填写有误"),
    CFCAProviderNotFound(5000003, "Provider丢失"),
    CaptchaErrorException(6000002, "验证码错误"),
    ExistIdCardException(6000005, "身份证已经被占用"),
    AuthException(9000001, "鉴权失败"),
    WechatRecognizeException(9000002, "微信识别失败"),
    WechatAPIException(9000003, "微信请求失败"),
    DuplicateException(9000004, "该用户已经注册过，请直接登陆"),
    NeedRegisterException(9000006, "用户不存在，请先注册"),
    ErrorAuthException(9000005, "不能授权给自己！"),
    BankNotFoundException(100000012, "银行未找到"),
    CityNotFoundException(100000013, "城市未找到"),
    CNAPSNotFoundException(100000014, "CNAPS未找到"),
    CIMSContractInvalidException(100000015, "CIMS合同非法"),
    WECHATAlivenessDetectLimitException(100000013, "微信人脸识别超过规定次数"),
    WECHATAlivenessDetectException(100000004, "微信人脸识别未通过"),
    SMSException(100000005, "短信发送失败，请重试"),
    NeedRetryException(100000006, "请让授权人重新发起授权"),
    CFCACreateContractException(100000007, "CFCA创建合同失败"),
    CFCASignContractException(100000009, "CFCA签署合同失败"),
    CIMSIllegalDataException(100000008, "CIMS数据非法"),
    FileConvertedException(100000010, "PDF转换失败"),
    BindBankException(100000012, "需要绑定银行卡信息"),
    AlgorithmErrorException(100000011, "算法错误"),
    CIMSFactorContractStatus(200000001, "获取CIMS合同状态失败");

    private final int code;
    private final String description;

    private ErrorCode(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public int getCode() {
        return code;
    }

    @Override
    public String toString() {
        return code + ": " + description;
    }
}
