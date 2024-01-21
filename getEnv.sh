#!bin/bash

aws secretsmanager get-secret-value --secret-id sbcntr/mysql \
| jq -r .SecretString \
| { read ss ; \
    echo -e "$ss"| DB_USERNAME="$(jq -r .username)"; \
    echo -e "$ss"| DB_PASSWORD="$(jq -r .password)"; \
    echo -e "$ss"| DB_CLIENT="$(jq -r .engine)"; \
    echo -e "$ss"| DB_HOST="$(jq -r .host)"; \
    echo -e "$ss"| DB_PORT="$(jq -r .port)"; \
}

echo $DB_USERNAME
echo $DB_PASSWORD
echo $DB_CLIENT
echo $DB_HOST
echo $DB_PORT