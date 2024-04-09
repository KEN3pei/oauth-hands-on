### Gradle Wrapperの導入（実行不要）

gradle wrapper --gradle-version 8.4

### ビルド

MySQLコンテナを立ち上げた状態で実行。

./gradlew build

### Local実行

./gradlew bootRun

### JOOQのコード生成タスクのみ実行

```shell
$ mysql.server stop
$ ./gradlew jooqCodegen -Pdb="127.0.0.1 user password"
```

### JOOQのコード生成タスクのみ除外してbuild

- `./gradlew build -Pdb="127.0.0.1 user password"`


### image作成

`docker image build -t authorization-server:v1 .`

### コンテナ起動

```shell
$ docker run --name authorization-server \
-e DB_HOST \
-e DB_PORT \
-e DB_NAME \
-e DB_USER \
-e DB_PASSWORD \
-it authorization-server:v1
```

docker-compose exec authorization_server sh

### API Document

https://documenter.getpostman.com/view/20019478/2sA35Mxy5S

### Localでのコード変更について

- コード保存時は、このディレクトリを1プロジェクトとして開いてcmd+sで保存するように。（パスがおかしくなる）
- コード変更後の反映コマンド
  - `./gradlew build -Pdb="127.0.0.1 user password"`
  - `docker compose up -d --build`

