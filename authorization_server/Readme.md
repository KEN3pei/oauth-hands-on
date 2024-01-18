### Gradle Wrapperの導入（実行不要）

gradle wrapper --gradle-version 8.4

### ビルド

MySQLコンテナを立ち上げた状態で実行。

./gradlew build

### Local実行

./gradlew bootRun

### image作成

`docker image build -t sbcntr-authorization-server:v1 .`
