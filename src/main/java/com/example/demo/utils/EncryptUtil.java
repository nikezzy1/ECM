package com.example.demo.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.example.demo.constants.EcmConstants;
import com.example.demo.exception.ErrorCode;
import com.example.demo.exception.ServiceException;
import org.apache.commons.codec.digest.DigestUtils;


import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.Date;
import java.util.function.Function;
import java.util.function.Supplier;

public class EncryptUtil {
    public static String SHA256(String originStr) {
        return DigestUtils.sha256Hex(originStr);
    }

    public static String generateToken(Long userId) {
        Date now = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(now);
        c.add(Calendar.HOUR, 24);
        now = c.getTime();
        Algorithm algorithmHS = Algorithm.HMAC256(EcmConstants.JWT_TOKEN);
        return JWT.create()
                .withExpiresAt(now)
                .withIssuer(userId.toString())
                .sign(algorithmHS);
    }

    public static void check(Long userId, String token) throws ServiceException {
        try {
            Algorithm algorithm = Algorithm.HMAC256(EcmConstants.JWT_TOKEN);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer(userId.toString())
                    .build();
            verifier.verify(token);
        } catch (TokenExpiredException ex) {
            throw new ServiceException(ErrorCode.TokenExpiredException);
        }
    }

    private static String getSHA1EncryptedString(String inputStr) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA");
        messageDigest.update(inputStr.getBytes());
        Supplier<String> convertMDToSHA1 = () -> new BigInteger(messageDigest.digest()).toString().toUpperCase();
        return convertMDToSHA1.get();

    }

    private static void checkAppId(String appId, Long ts) throws ServiceException {
        // todo check时间
        if (appId.equals("arb") || appId.equals("cims")) {
            return;
        }
        throw new ServiceException(ErrorCode.AuthException);
    }

    public static <T> String calculateSign(String appId, String appSecret, long ts, T t) throws ServiceException {
        checkAppId(appId, ts);
        Function<String, String> getParams = (x) -> new StringBuffer(x).append(appSecret).append(t.toString()).toString();
        try {
            return getSHA1EncryptedString(getParams.apply(appId));
        } catch (NoSuchAlgorithmException e) {
            throw new ServiceException(ErrorCode.AlgorithmErrorException);
        }
    }
}
