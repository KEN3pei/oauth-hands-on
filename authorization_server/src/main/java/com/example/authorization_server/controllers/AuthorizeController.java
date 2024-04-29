package com.example.authorization_server.controllers;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import com.example.authorization_server.controllers.responses.TokenEndpointResponse;
import com.example.authorization_server.domains.ClientCredencial;
import com.example.authorization_server.jooq.tables.records.ClientsRecord;
import com.example.authorization_server.services.ApproveService;
import com.example.authorization_server.services.AuthorizeService;
import com.example.authorization_server.services.TokenService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mysql.cj.x.protobuf.MysqlxDatatypes.Any;

@Controller
public class AuthorizeController {

    private final AuthorizeService authorizeService;
    private final ApproveService approveService;
    private final TokenService tokenService;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public AuthorizeController(
        AuthorizeService authorizeService,
        ApproveService approveService,
        TokenService tokenService
    ){
        this.authorizeService = authorizeService;
        this.approveService = approveService;
        this.tokenService = tokenService;
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

            Object[] res = this.approveService.execute(form);
            String code = (String)res[0];
            Map<String, String> query = (Map<String, String>)res[1];
            logger.info("<<< CREATE RESPONSE FROM AUTHCODE & QUERY>>>");

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
    public ResponseEntity tokenEndpoint(
        @RequestBody TokenRequest request,
        @RequestHeader("authorization") String auth
    ) {
        logger.info("START POST /token");
        // 1.Basic認証で渡されたHeader内のIDとPaswordを取得（client_idとclient_secretをHeaderから取得）
        // 「basic client_id:client_secret」から「client_id:client_secret」を切り出す
        String clientCredential = auth.substring(6);
        String[] clientCredentialArray = clientCredential.split(":");
        logger.info("<<< CREDENCIALS >>>");
        logger.info(clientCredentialArray[0]);
        logger.info(clientCredentialArray[1]);

        ClientCredencial credencial = new ClientCredencial(
            clientCredentialArray[0], 
            clientCredentialArray[1]
        );
        try {
            String accessToken = tokenService.execute(credencial, request);
            TokenEndpointResponse res = new TokenEndpointResponse();
            res.setAccessToken(accessToken);
            res.setTokenType("Bearer");
            return new ResponseEntity<TokenEndpointResponse>(res, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<HashMap<String, String>>(new HashMap<String, String>(), HttpStatus.BAD_REQUEST);
        }
    }
}
