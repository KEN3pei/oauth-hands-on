#!bin/bash

aws secretsmanager get-secret-value --secret-id sbcntr/mysql \
| jq -r .SecretString \
| { read ss ; \
    echo -e "$ss"| export DB_USERNAME="$(jq -r .username)"; \
    echo -e "$ss"| export DB_PASSWORD="$(jq -r .password)"; \
    echo -e "$ss"| export DB_CLIENT="$(jq -r .engine)"; \
    echo -e "$ss"| export DB_HOST="$(jq -r .host)"; \
    echo -e "$ss"| export DB_PORT="$(jq -r .port)"; \
}