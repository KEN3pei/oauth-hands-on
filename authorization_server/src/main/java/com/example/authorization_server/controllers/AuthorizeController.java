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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.authorization_server.controllers.requests.AuthorizeRequest;
import com.example.authorization_server.controllers.requests.ClientAuthFormRequest;
import com.example.authorization_server.controllers.requests.TokenRequest;
import com.example.authorization_server.jooq.tables.records.ClientsRecord;
import com.example.authorization_server.services.ApproveService;
import com.example.authorization_server.services.AuthorizeService;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class AuthorizeController {

    private final AuthorizeService authorizeService;
    private final ApproveService approveService;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public AuthorizeController(
        AuthorizeService authorizeService,
        ApproveService approveService
    ){
        this.authorizeService = authorizeService;
        this.approveService = approveService;
    }

    // 認証が必須のEndpointとする
    @GetMapping("/authorize")
    public String authorizeEndpoint(@RequestParam Map<String, String> params, Model model) {
        try {
            logger.info("START GET /authorize");

            ObjectMapper mapper = new ObjectMapper();
            AuthorizeRequest request = mapper.convertValue(params, AuthorizeRequest.class);
            logger.info("MAPPED REQUEST: ", request);
            
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
            client.put("reqId", reqId);

            model.addAllAttributes(client);//ResponseクラスとClientドメインが必要そう
            return "authorize";

        }catch(Exception e){
            e.printStackTrace();
            logger.info("400 ERROR GET /authorize");
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
    public String clientAuthEndpoint(@ModelAttribute ClientAuthFormRequest form, RedirectAttributes redirectAttributes) {
        try {
            logger.info("START POST /approve");
            logger.info(form.getApprove());
            logger.info(form.getReqId());
            String[] scopes = form.getScope();
            for(int i = 0; i < scopes.length ; i++){
                logger.info(scopes[i]);
            }

            Object[] res = this.approveService.execute(form);
            String code = (String)res[1];
            Map<String, String> query = (Map<String, String>)res[2];

            Map<String, String> params = new HashMap<String, String>(){
                {
                    put("code", code);
                    put("state", query.get("state"));
                }
            };
            redirectAttributes.addAllAttributes(params);
            return "redirect:"+query.get("redirect_uri");
        }catch(Exception e){
            e.printStackTrace();
            return "400";
        }
    }

    // バックチャネルでクライアントから叩かれるトークン生成エンドポイント
    @PostMapping("/token")
    public String tokenEndpoint(@RequestBody TokenRequest request, @RequestHeader("Authorization") String auth) {
        // 1.Basic認証で渡されたHeader内のIDとPaswordを取得（client_idとclient_secretをHeaderから取得）
        // 「basic client_id:client_secret」から「client_id:client_secret」を切り出す
        String clientCredential = auth.substring(6);
        String[] clientCredentialArray = clientCredential.split(":");

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
