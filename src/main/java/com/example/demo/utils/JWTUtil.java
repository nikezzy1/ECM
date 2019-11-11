package com.example.demo.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.demo.exception.ErrorCode;
import com.example.demo.exception.ServiceException;

import java.sql.Date;
import java.time.LocalDate;

public class JWTUtil {
    public static String generateToken(long naturalPersonId, String channelId) throws ServiceException {
        try {
            Algorithm algorithm = Algorithm.HMAC256("c5b33550c8b94a39a2fcd17ea045d1c1");
            return JWT.create()
                    .withIssuer(String.valueOf(naturalPersonId))
                    .withExpiresAt(Date.valueOf(LocalDate.now().plusDays(7)))
                    .withClaim("channelId", channelId)
                    .sign(algorithm);
        } catch (JWTCreationException exception) {
            throw new ServiceException(ErrorCode.AuthException);
        }
    }

    public static long getNaturalPersonId(String token) throws ServiceException {
        try {
            DecodedJWT jwt = JWT.decode(token);
            if (jwt.getExpiresAt().before(Date.valueOf(LocalDate.now()))) {
                throw new ServiceException(ErrorCode.TokenExpiredException);
            }
            return Long.valueOf(jwt.getIssuer());
        } catch (JWTDecodeException exception) {
            throw new ServiceException(ErrorCode.AuthException);
        }
    }
}
