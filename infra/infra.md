### サブネット

| 用途 | NW区分 | AZ | CIDR | サブネット名（名前タグ）|
| ---- | ---- | ---- | ---- | ---- |
| Ingress | Public | 1a | 10.0.0.0/24 | sbcntr-subnet-public-ingress-1a |
| Ingress | Public | 1c | 10.0.0.1/24 | sbcntr-subnet-public-ingress-1c |
| アプリケーション | Private | 1a | 10.0.8.0/24 | sbcntr-subnet-private-container-1a |
| アプリケーション | Private | 1c | 10.0.9.0/24 | sbcntr-subnet-private-container-1c |
| DB | Private | 1a | 10.0.16.0/24 | sbcntr-subnet-private-db-1a |
| DB | Private | 1c | 10.0.17.0/24 | sbcntr-subnet-private-db-1c |
| 管理 | Public | 1a | 10.0.240.0/24 | sbcntr-subnet-public-management-1a |
| 管理（予備） | Public | 1c | 10.0.241.0/24 | sbcntr-subnet-public-management-1c |
| Egress | Private | 1a | 10.0.248.0/24 | sbcntr-subnet-private-egress-1a |
| Egress | Private | 1c | 10.0.249.0/24 | sbcntr-subnet-private-egress-1c |
|  |  |  |  |  |

### ルートテーブル

| 紐づけるサブネット | 送信先 | ターゲット |
| ---- | ---- | ---- |
| Ingress用サブネット | 10.0.0.0/16 | local |
|  | 0.0.0.0/0 | igw-{id} |
| それ以外のサブネット | 10.0.0.0/16 | local |
|  |  |  |

### コンテナレジストリ
- sbcntr-authorization-server
- sbcntr-protected-resource

### VPCエンドポイント名
- sbcntr-vpce-ecr-api
- sbcntr-vpce-ecr-dkr
- sbcntr-vpce-s3

### 踏み台サーバ
- キーペア名: sbcntr-bastion-keypair
- Github連携用キーペア名: sbcntr-bastion-github-key
- インスタンス名: sbcntr-bastion
- 踏み台サーバ用 IAMRole名: sbcntr-bastion-role
  - 紐付けるポリシー
  - ECR: sbcntr-AccessingECRRepositoryPolicy
  - SecretManager: EC2ToAccessSecretsPolicy 

ssh接続
`ssh -i "sbcntr-bastion-keypair.pem" ec2-user@{PublicIP}`

secret-managerにgithub-keyを登録するコマンド
```shell
$ aws secretsmanager create-secret \
--name sbcntr-bastion-github-key-202401131558 \
--description "Github接続用の秘密鍵" \
--secret-string "$(cat sbcntr-bastion-github-key)" \
--profile profile-name
```

秘密鍵取得コマンド
```shell
$ aws secretsmanager get-secret-value --secret-id sbcntr-bastion-github-key-202401131558 \
    --profile profile-name \
    | jq -r .SecretString
```

EC2 setup
```shell
$ aws configure
$ sudo yum install jq
$ sudo yum install -y  git-all
$ vi ~/.ssh/config
Host github.com
  HostName github.com
  User git
  Port 22
  IdentityFile ~/.ssh/github_key
$ chmod 600 ~/.ssh/config
$ aws secretsmanager get-secret-value --secret-id sbcntr-bastion-github-key-202401131558 \
    | jq -r .SecretString > ~/.ssh/github_key
$ chmod 600 ~/.ssh/github_key
$ ssh -T git@github.com
```