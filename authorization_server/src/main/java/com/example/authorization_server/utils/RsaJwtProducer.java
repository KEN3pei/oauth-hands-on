package com.example.authorization_server.utils;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.OffsetDateTime;
import java.util.Base64;
import java.util.UUID;
import java.io.File;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.authorization_server.config.EnvProperties;

@Component
public class RsaJwtProducer {
    private String keyPath;
    private ResourceLoader resourceLoader;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    
    @Autowired
    public RsaJwtProducer(
        EnvProperties envProperties,
        ResourceLoader resourceLoader) {
        this.keyPath = envProperties.getPrikeypath();
        this.resourceLoader = resourceLoader;
    }

    public String generateToken() {
        Algorithm alg = Algorithm.RSA256(createPrivateKey());
        
        // トークンのClaim設計について参照した記事
        // https://kamichidu.github.io/post/2017/01/24-about-json-web-token/
        String token = JWT.create()
                .withJWTId(UUID.randomUUID().toString())
                .withIssuer("https://idp.example.com")
                .withSubject("AccessToken")
                .withArrayClaim("Audience", new String[] { "https://api.example.com" })
                .withExpiresAt(OffsetDateTime.now().plusMinutes(60).toInstant())
                .withIssuedAt(OffsetDateTime.now().toInstant())
                .withClaim("client_id", 1)
                .withArrayClaim("client_scope", new String[] { "foo", "bar" })
                .sign(alg);
        return token;
    }

    private RSAPrivateKey createPrivateKey() {
        try {
            // Resource resource = resourceLoader.getResource("classpath:" + this.keyPath);
            InputStream resource = resourceLoader.getResource("classpath:" + this.keyPath).getInputStream();
            try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(resource))) {
                String line;
                var pem = new StringBuilder();
                while ((line = bufferedReader.readLine()) != null) {
                    pem.append(line);
                }

                String privateKeyPem = pem.toString()
                        .replace("-----BEGIN PRIVATE KEY-----", "")
                        .replaceAll(System.lineSeparator(), "")
                        .replace("-----END PRIVATE KEY-----", "");
                logger.info("privateKeyPem: " + privateKeyPem);
                
                byte[] encoded = Base64.getDecoder().decode(privateKeyPem);
                PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(encoded);
                KeyFactory keyFactory = KeyFactory.getInstance("RSA");
                return (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
            }
        } catch (IOException | NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new IllegalStateException(e);
        }
    }

    // public static void main(String[] args) {
    //     String keyPath = System.getenv("KEY_PATH");
    //     System.out.println(new RsaJwtProducer(keyPath).generateToken());
    // }
}
