package com.example.protected_resource.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.example.protected_resource.config.EnvProperties;

// 参照記事 https://developer.mamezou-tech.com/blogs/2022/12/25/rsa-java-jwt/#%E3%83%88%E3%83%BC%E3%82%AF%E3%83%B3%E3%81%AE%E6%A4%9C%E8%A8%BC%E5%AE%9F%E8%A3%85rsajwtconsumer
@Component
public class RsaJwtConsumer {
    private String publicKeyPath;
    private ResourceLoader resourceLoader;

    @Autowired
    public RsaJwtConsumer(EnvProperties envProperties, ResourceLoader resourceLoader) {
        this.publicKeyPath = envProperties.getPubkeypath();
        this.resourceLoader = resourceLoader;
    }

    public DecodedJWT verifyToken(String token) {
        Algorithm alg = Algorithm.RSA256(createPublicKey());
        //TODO: JWT解決処理を実装する
        JWTVerifier verifier = JWT.require(alg)
                .withIssuer("https://idp.example.com")
                .acceptExpiresAt(5)//トークン生成側と検証側のシステム時刻のズレを5秒まで許容する
                .build();
        try {
            return verifier.verify(token);
        } catch (JWTVerificationException e) {
            System.out.println("JWT verification failed..");
            throw e;
        }
    }

    private RSAPublicKey createPublicKey() {
        try {
            InputStream resource = resourceLoader.getResource("classpath:" + this.publicKeyPath).getInputStream();
            try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(resource))) {
                String line;
                var pem = new StringBuilder();
                while ((line = bufferedReader.readLine()) != null) {
                    pem.append(line);
                }

                String publicKeyPem = pem.toString()
                    .replace("-----BEGIN PUBLIC KEY-----", "")
                    .replaceAll(System.lineSeparator(), "")
                    .replace("-----END PUBLIC KEY-----", "");
                
                byte[] encoded = Base64.getDecoder().decode(publicKeyPem);
                X509EncodedKeySpec keySpec = new X509EncodedKeySpec(encoded);
                KeyFactory keyFactory = KeyFactory.getInstance("RSA");
                return (RSAPublicKey) keyFactory.generatePublic(keySpec);
            }
        } catch (IOException | NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new IllegalStateException(e);
        }
    }

    // public static void main(String[] args) {
    // DecodedJWT jwt = new RsaJwtConsumer().verifyToken(args[0]);

    // System.out.println("----- DecodedJWT -----");
    // System.out.println("alg:" + jwt.getAlgorithm());
    // System.out.println("typ:" + jwt.getType());
    // System.out.println("issuer:" + jwt.getIssuer());
    // System.out.println("subject:" + jwt.getSubject());
    // System.out.println("expiresAt:" + jwt.getExpiresAt());
    // System.out.println("issuerAt:" + jwt.getIssuedAt());
    // System.out.println("JWT-ID:" + jwt.getId());
    // System.out.println("email:" + jwt.getClaim("email").asString());
    // System.out.println("groups:" + jwt.getClaim("groups")
    // .asList(String.class).stream()
    // .collect(Collectors.joining(",")));
    // }
}
