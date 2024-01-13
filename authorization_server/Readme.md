### Gradle Wrapperの導入（実行不要）

gradle wrapper --gradle-version 8.4

### ビルド

TODO: localではlocalhost、コンテナ上ではhost.docker.internalをホストにした上で以下ビルドコマンドを叩かないといけないのを治す。

./gradlew build

### 実行

./gradlew bootRun