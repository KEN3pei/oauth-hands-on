package com.example.authorization_server.services;

import java.util.Optional;

import org.jooq.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.authorization_server.controllers.requests.TokenRequest;
import com.example.authorization_server.domains.ClientCredencial;
import com.example.authorization_server.domains.code.CodeQuery;
import com.example.authorization_server.infrastructure.client.ClientRepository;
import com.example.authorization_server.infrastructure.code.CodeRepository;
import com.example.authorization_server.infrastructure.token.AccessTokenRepository;
import com.example.authorization_server.jooq.com.example.authorization_server.jooq.tables.records.ClientsRecord;
import com.example.authorization_server.jooq.com.example.authorization_server.jooq.tables.records.CodesRecord;
import com.example.authorization_server.jooq.com.example.authorization_server.jooq.tables.records.TokenRecord;
import com.example.authorization_server.services.specification.BasicAuthorizationCodeFlow;
import com.example.authorization_server.services.util.GenerateAccessToken;
import com.example.authorization_server.services.util.PasswordValidator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class TokenService implements BasicAuthorizationCodeFlow {
    private final ClientRepository clientRepository;
    private final CodeRepository codeRepository;
    private final AccessTokenRepository accessTokenRepository;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public TokenService(
            ClientRepository clientRepository,
            CodeRepository codeRepository,
            AccessTokenRepository accessTokenRepository) {
        this.clientRepository = clientRepository;
        this.codeRepository = codeRepository;
        this.accessTokenRepository = accessTokenRepository;
    }

    public String execute(ClientCredencial credencial, TokenRequest request) throws Exception {
        // クライアント認証
        if (!this.basicAttempt(credencial)) {
            throw new Exception("unauthentication user");
        }
        // クライアント認証方法の重複確認
        if (!this.isDuplicateAuthenticationMethods()) {
            throw new Exception("duplicate client authentication");
        }
        // グラントタイプ確認
        if (!this.grantTypeCheck(request.getGrantType())) {
            throw new Exception("not allowed grant type");
        }
        // 認可コードが正当なクライアントからのリクエストであることを確認
        if (!this.authCodeValidation(request.getCode(), credencial.clientId)) {
            throw new Exception("invalid request by user");
        }
        // access_tokenの生成と保存
        String accessToken = GenerateAccessToken.generate();
        TokenRecord tokenRecord = new TokenRecord();
        tokenRecord.setAccessToken(accessToken);
        tokenRecord.setClientId(credencial.clientId);
        this.accessTokenRepository.save(tokenRecord);

        return accessToken;
    }

    public boolean basicAttempt(ClientCredencial credencial) {
        Optional<ClientsRecord> optClient = this.clientRepository.findByClientId(credencial.clientId);
        ClientsRecord client;
        // clientがそもそも存在するか
        if (optClient.isPresent()) {
            client = optClient.get();
            logger.info("<<< FIND CLIENT BY ID >>>");
            logger.info(client.getClientId());
        } else {
            return false;
        }
        // clientのpasswordが一致するか
        return (new PasswordValidator()).hashMatch(
                credencial.clientSecret,
                client.getSecret());
    }

    public boolean isDuplicateAuthenticationMethods() {
        // TODO
        return true;
    }

    public boolean grantTypeCheck(String grantType) {
        return grantType.equals("authorization_code");
    }

    public boolean authCodeValidation(String code, String clientId)
            throws JsonMappingException, JsonProcessingException {
        CodesRecord codeRecord = this.codeRepository.findByCode(code);

        JSON query = codeRecord.getQuery();
        CodeQuery codeQuery = null;
        ObjectMapper mapper = new ObjectMapper();
        codeQuery = mapper.readValue(query.toString(), new TypeReference<CodeQuery>() {
        });

        // リクエストしたcodeが有効であるか確認
        // if (!codeQuery.expiredAt > now) {
        // 今はこのようなカラムはない
        // }

        // 認可コードの削除
        this.codeRepository.delete(code);

        // リクエストしたcodeの所有者が適切か確認
        if (!codeQuery.clientId.equals(clientId)) {
            return false;
        }

        return true;
    }
}
