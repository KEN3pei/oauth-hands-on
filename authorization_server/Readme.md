### Gradle Wrapperの導入（実行不要）

gradle wrapper --gradle-version 8.4

### ビルド

MySQLコンテナを立ち上げた状態で実行。

./gradlew build

### Local実行

./gradlew bootRun

### JOOQのコード生成タスクのみ実行

./gradlew jooqCodegen -PdbHost="127.0.0.1"

### JOOQのコード生成タスクのみ除外してbuild

./gradlew build -x jooqCodegen

### image作成

`docker image build -t authorization-server:v1 .`
