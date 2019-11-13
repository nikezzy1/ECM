package ls.ecm.utils;

import com.alibaba.fastjson.JSON;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.OSSObject;
import lombok.extern.slf4j.Slf4j;
import ls.ecm.exception.ErrorCode;
import ls.ecm.exception.ServiceException;
import ls.ecm.model.enums.FileType;
import org.apache.commons.io.IOUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

import static ls.ecm.constants.WechatConstants.*;


@Slf4j
public class OSSUtil {
    private static String uploadFile(FileType fileType, String content) throws ServiceException {
        String fileName = RandomString.generateFileName();
        uploadFile(fileType, fileName, content);
        return fileName;
    }

    private static String uploadFile(FileType fileType, String fileName, String content) throws ServiceException {
        String endpoint = "http://oss-cn-hangzhou.aliyuncs.com";
        String accessKeyId = ALI_OSS_APP_KEY;
        String accessKeySecret = ALI_OSS_APP_SECRET;
        String bucketName = ALI_OSS_BUCKET_NAME;
        OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        ossClient.putObject(bucketName, fileType.getMsg() + "/" + fileName, new ByteArrayInputStream(content.getBytes()));
        ossClient.shutdown();
        return fileName;
    }

    private static String downloadFile(FileType fileType, String fileName) throws ServiceException {
        String endpoint = "http://oss-cn-hangzhou.aliyuncs.com";
        String accessKeyId = ALI_OSS_APP_KEY;
        String accessKeySecret = ALI_OSS_APP_SECRET;
        String bucketName = ALI_OSS_BUCKET_NAME;

        OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);

        if (fileName == null) {
            throw new ServiceException(ErrorCode.EmptyFileNameException);
        }
        OSSObject ossObject = ossClient.getObject(bucketName, fileType.getMsg() + "/" + fileName);

        try {
            return convert(ossObject.getObjectContent(), Charset.forName("UTF-8"));
        } catch (IOException e) {
            log.error("【OSS】异常 :" + "堆栈信息 ："+ExceptionUtil.getStackTrace(e).substring(0,120));
            throw new ServiceException(ErrorCode.HTTPIOException);
        }
    }

    public static String convert(InputStream inputStream, Charset charset) throws IOException {
        return IOUtils.toString(inputStream, charset);
    }

    public static String uploadIdCard(String content) throws ServiceException {
        return uploadFile(FileType.ID_CARD, content);
    }

    public static String uploadOSSContract(String fileName, String content) throws ServiceException {
        return uploadFile(FileType.CONTRACT, fileName, content);
    }

    public static String uploadImage(String content) throws ServiceException {
        return uploadFile(FileType.IMAGE, content);
    }

//    public static String getContract(ContractType contractType, String channelId) throws ServiceException {
//        log.info("channelId:" + channelId + "contractInfo: " + JSON.toJSONString(contractType));
//        ChannelInfo channelInfo = StringUtil.splitChannelId(channelId);
//        if (channelInfo.getFactoringServiceType() == FactoringServiceType.RECOVERY) {
//            return downloadFile(FileType.TEST_CONTRACT, "recourse_" + contractType.getFileName());
//        }
//        return downloadFile(FileType.TEST_CONTRACT, contractType.getFileName());
//    }
}

