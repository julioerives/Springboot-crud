package com.register.registers.services.jwtServices;

import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JWTService {
    private String secretKey = "";
    public JWTService(){
        generateSecretKey();
    }
    public String generateToken(String name){
        Map<String,Object> claims =new HashMap<>();
        return Jwts.builder().claims().add(claims).subject(name).issuedAt(new Date(System.currentTimeMillis())).expiration(new Date(System.currentTimeMillis()*60*60*10))
        .and()
        .signWith(getKey())
        .compact();
    }
    private SecretKey getKey() {
        byte[] bytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(bytes);
    }
    private void generateSecretKey() {
        try{
            KeyGenerator keyGen = KeyGenerator.getInstance("HmacSHA256");
            SecretKey sKey = keyGen.generateKey();
            secretKey = Base64.getEncoder().encodeToString(sKey.getEncoded());
        }catch(Exception e){
            System.out.println(e.getMessage());
            throw new RuntimeException();
        }
    }
    public String extractEmail(String token){
        Claims claims = extractAllClaims(token);
        return claims.getSubject();
    }
    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
    
    public Boolean validateToken(String token){
        try {
            Claims claims = extractAllClaims(token);
            Date expirationDate = claims.getExpiration();
            return !expirationDate.before(new Date());
        } catch (Exception e) {
            return false;
        }
    }
}