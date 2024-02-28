package com.example.authorization_server.controllers;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.authorization_server.controllers.requests.AuthorizeRequest;
import com.example.authorization_server.controllers.requests.ClientAuthRequest;
import com.example.authorization_server.controllers.requests.TokenRequest;
import com.example.authorization_server.jooq.tables.records.ClientsRecord;
import com.example.authorization_server.services.AuthorizeService;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class AuthorizeController {

    private final AuthorizeService authorizeService;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public AuthorizeController(
        AuthorizeService authorizeService
    ){
        this.authorizeService = authorizeService;
    }

    // 認証が必須のEndpointとする
    @GetMapping("/authorize")
    public String authorizeEndpoint(@RequestParam Map<String, String> params, Model model) {
        try {
            logger.info("START GET /authorize");

            ObjectMapper mapper = new ObjectMapper();
            AuthorizeRequest request = mapper.convertValue(params, AuthorizeRequest.class);

            Object[] res = this.authorizeService.execute(request);

            ClientsRecord clientRecord = (ClientsRecord)res[0];
            String reqId = (String)res[1];
            String scope = (String)res[2];

            Map<String, Object> client = new HashMap<>();
            client.put("client_id", request.getClientId());
            client.put("client_secret", request.getClientSecret());
            client.put("redirect_uri", request.getRedirectUri());
            List<String> scopes = Arrays.asList("foo", "bar");
            client.put("scopes", scopes);
            client.put("reqid", reqId);

            model.addAllAttributes(client);
            return "authorize";

        }catch(Exception e){
            return "400";
        }
        /* responseの例
         * {
         *    "client_id": "oauth-client-1",
         *    "client_secret": "oauth-client-secret-1",
         *    "redirect_uris": "http://localhost:9000/callback",
         *    "scope": "foo bar"
         *    // formのhiddenに含めてcsrf対策に使用する
         *    "reqid": "reqid"
         *  }
         */
    }

    //クライアントに認可コードを返すエンドポイント
    @PostMapping("/approve")
    public String clientAuthEndpoint(@RequestBody ClientAuthRequest request) {
        // 1.req_idを確認してcsrf攻撃の可能性がないかチェック（requestsテーブルをreq_idで検索）
        // 2.approveされたかのチェック（denyならcallbackにエラーを返す）
        // 3.認可コードタイプかチェック（type=code以外ならcallbackにエラーを返す）
        // 4.認可コード生成＆保存（認可コードをキーにしてリクエストQueryを保存）
        // 5.認可コードとstateを付与して、/callbackにリダイレクト
        return "redirect";
    }

    // バックチャネルでクライアントから叩かれるトークン生成エンドポイント
    @PostMapping("/token")
    public String tokenEndpoint(@RequestBody TokenRequest request) {
        // 1.Basic認証で渡されたHeader内のIDとPaswordを取得（client_idとclient_secretをHeaderから取得）
        // 2.bodyとheaderの両方にCredencialが含まれていないかチェック（両方あれば401を返す）
        // 3.client_idでclientテーブルから検索（一致するものがなければ401を返す）
        // 4.grant_type=authorization_codeであることを確認
        // 5.認可コード一時保存テーブルから、リクエストされた認可コードと一致するqueryをDBから取得
        // 6.認可コードレコードに含まれるclient_idとリクエストされたclient_idをチェック（認可コードレコードはこのタイミングで削除）
        // 7.アクセストークンの生成と保存(scopeも一緒に保存)
        return "authorize";
        /* 以下をレスポンスに含めて返す
         * access_token: access_token
         * token_type: "Bearer"
         * scope: "foo bar" // 認可コード一時保存テーブルから取得したscope情報を半角区切りで足し合わせた文字列
         */
    }
}
