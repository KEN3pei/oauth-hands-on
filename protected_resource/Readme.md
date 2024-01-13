### Gradle Wrapperの導入（実行不要）

gradle wrapper --gradle-version 8.4

### ビルド

./gradlew build

### 実行

./gradlew bootRun

### docker build image

docker build . -t protected_resource_app

### docker run

docker run protected_resource_app --name protected_resource_app