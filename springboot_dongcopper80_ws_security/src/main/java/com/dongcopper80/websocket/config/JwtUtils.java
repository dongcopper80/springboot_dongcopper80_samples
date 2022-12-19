/*
 * The MIT License
 *
 * Copyright 2022 Nguyễn Thúc Đồng (dongcopper80).
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.dongcopper80.websocket.config;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.dongcopper80.websocket.repository.SysRefreshTokenRepository;
import com.dongcopper80.websocket.service.UserDetailsImpl;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

/**
 *
 * @author Nguyễn Thúc Đồng (dongcopper80)
 */
@Component
public class JwtUtils {

    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    @Autowired
    SysRefreshTokenRepository sysRefreshTokenRepository;

    @Value("${jwt.RefreshExpirationMs}")
    private Long refreshTokenDurationMs;

    @Value("${jwt.RefreshSecret}")
    private String jwtRefreshSecret;

    @Value("${jwt.AccessSecret}")
    private String jwtAccessSecret;

    @Value("${jwt.ExpirationMs}")
    private int jwtExpirationMs;

    public String generateAccessToken(UserDetailsImpl userPrincipal) {
        return generateAccessTokenFromUsername(userPrincipal.getUsername());
    }

    public String createRefreshToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + refreshTokenDurationMs))
                .signWith(SignatureAlgorithm.HS512, jwtRefreshSecret)
                .compact();
    }

    public String generateAccessTokenFromUsername(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(SignatureAlgorithm.HS512, jwtAccessSecret)
                .compact();
    }

    public String getUserNameFromRefreshToken(String token) {
        return Jwts.parser()
                .setSigningKey(jwtRefreshSecret)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public String getUserNameFromAccessToken(String token) {
        return Jwts.parser()
                .setSigningKey(jwtAccessSecret)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean validateExpiration(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtAccessSecret).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e) {
            logger.error("Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
        }
        return false;
    }

    public boolean validateRefreshExpiration(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtRefreshSecret).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e) {
            logger.error("Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
        }
        return false;
    }

    @Transactional
    public void deleteByRefreshToken(String token) {
        sysRefreshTokenRepository.deleteRefreshTokenByToken(token);
    }
}
