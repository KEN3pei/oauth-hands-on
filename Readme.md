## JavaのSpringBootでOAuth（認可コードフロー）の実装をしてみる

### やること

1. まずはSpringBootで簡単なwebアプリケーションを実装してみる
2. シンプルなOAuthに必要な3つのコンポーネントを実装してみる

### アプリの内容

CRUD操作を認可の仕組みで制御できるようにするだけのアプリケーション

### ディレクトリ構成

3つのコンポーネントのモジュールに分け、モノレポ構成とする。
```
oauth(root)
└ authorizationServer（認可サーバ）
└ client（webブラウザで操作するclient）
└ protectedResource（保護対象リソースサーバ）
```

### database

MySQLを使用する。

必要な情報
- OAuthクライアント情報テーブル
  - リダイレクトURL
  - クライアントID: "oauth-client-1"
  - シークレット: "oauth-client-secret-1"
  - スコープ
```json
{
	"client_id": "oauth-client-1",
	"client_secret": "oauth-client-secret-1",
	"redirect_uris": ["http://localhost:9000/callback"],
  "scope": "update index"
}
```

- リクエスト情報一時格納テーブル
 - req_id(PK)
 - query(JSON)
  ```json
  {
    "client_id": "",
    "redirect_uri": "",
    "scope": "",
  }
  ```
- 認可コード一時保存テーブル
 - code(PK)
 - query(JSON)

- アクセストークン格納用テーブル
  - リソースサーバと認可サーバで共有する（トークンイントロスペクションエンドポイントを使わない）
  - client_id(PK)
  - access_token
  - scope

- ユーザー情報テーブル
  - id
  - email
  - introduce
  - password
  ```SQL
  create table users (
    id character varying(10) not null
    , email character varying(100) not null unique
    , introduce character varying(500)
    , password character varying(500) not null
    , primary key (id)
  );

  INSERT INTO users(id, email, introduce, password)
  VALUES("1", "test1@example.com", "testtest", "$2a$08$5GJh.woO8kmlrkBLEhxL1uAe1ejsp8Sp.li.TTAIa9zP45tLbhNp6");
  -- https://toolbase.cc/text/bcrypt
  ```

### ORM

JOOQ（ジューク）

TODO:JOOQで生成したコードはgit管理すべきではない
- https://nabedge.mixer2.org/2019/03/jooq-flyway-sample.html
- Flywayで別プロジェクトに分けて将来的には管理する。

### client（clientサーバ）

テンプレートエンジンのThymeleafを使用する。
- GET /authorize（認可エンドポイントへのリダイレクト用API）
- GET /callback（リソース所有者が認可した後にリダイレクトされ認可コードを受け取るURIURI）

### authorizationServer（認可サーバ）

認可エンドポイントとトークンエンドポイントを持つ。リフレッシュトークンは発行しない。
- GET /authorize (認可エンドポイント)
  - リソース所有者の認証をSession認証で行う
- POST /token     (トークンエンドポイント)
- POST /approve   (リソース所有者によるクライアントの認可)

### protectedResource（保護対象リソースサーバ）

CRUD機能を持つ
アクセストークンのスコープ(scope)によって操作できるか変える

- GET /
- POST /regist
- PUT /edit
- DELETE /delete

## コンテナ起動

```shell
$ docker network create local-bridge-mysql
$ docker compose up -d
```

