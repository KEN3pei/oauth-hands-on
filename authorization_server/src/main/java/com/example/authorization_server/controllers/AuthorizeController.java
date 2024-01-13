package com.example.authorization_server.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthorizeController {

    // 認証が必須のEndpointとする
    @GetMapping("/authorize")
    public String authorizeEndpoint() {
        // 1.Clientをclient_idで取得
        // 2.redirect_uriの検証
        // 3.scopeを半角で配列に分割「"foo bar"」->["foo","bar"]
        // 4.リクエストされたscopeの範囲がClientの範囲を超えていないかチェック
        // 5.request queryをDBに保存
        return "authorize";
        /* 6.以下を含めてviewで返す
         * {
         *    // リソース所有者がどのクライアントを承認するか判断するためにViewに渡す
         *    "client_id": "oauth-client-1",
         *    "client_secret": "oauth-client-secret-1",
         *    "redirect_uris": ["http://localhost:9000/callback"],
         *    "scope": "foo bar"
         *    // formのhiddenに含めてcsrf対策に使用する
         *    "reqid": "reqid"
         *  }
         */
    }

    //クライアントに認可コードを返すエンドポイント
    @PostMapping("/approve")
    public String clientAuthEndpoint() {
        // 1.req_idを確認してcsrf攻撃の可能性がないかチェック（requestsテーブルをreq_idで検索）
        // 2.approveされたかのチェック（denyならcallbackにエラーを返す）
        // 3.認可コードタイプかチェック（type=code以外ならcallbackにエラーを返す）
        // 4.認可コード生成＆保存（認可コードをキーにしてリクエストQueryを保存）
        // 5.認可コードとstateを付与して、/callbackにリダイレクト
        return "redirect";
    }

    // バックチャネルでクライアントから叩かれるトークン生成エンドポイント
    @PostMapping("/token")
    public String tokenEndpoint() {
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
