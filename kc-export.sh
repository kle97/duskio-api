export KEYCLOAK_ADMIN=admin
export KEYCLOAK_ADMIN_PASSWORD=123456
export JAVA_OPTS=-Dh2.bindAddress=localhost
kc.sh -cf keycloak.conf export --realm duskio --file kc-dev.json