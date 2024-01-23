#!bin/bash

aws secretsmanager get-secret-value --secret-id sbcntr/mysql \
| jq -r .SecretString \
| { read ss ; \
    # exportで書き込みたかったが子プロセスから設定するスマートな方法がなかった
    echo -e "$ss"| echo DB_USER="$(jq -r .username)"; \
    echo -e "$ss"| echo DB_PASSWORD="$(jq -r .password)"; \
    echo -e "$ss"| echo DB_CLIENT="$(jq -r .engine)"; \
    echo -e "$ss"| echo DB_HOST="$(jq -r .host)"; \
    echo -e "$ss"| echo DB_PORT="$(jq -r .port)"; \
    echo -e "$ss"| echo DB_NAME="oauth_db"; \
} | sudo tee /etc/environment > /dev/null

