#!/bin/bash
export KEYCLOAK_ADMIN=admin
export KEYCLOAK_ADMIN_PASSWORD=123456
export JAVA_OPTS=-Dh2.bindAddress=localhost

if [ "$1" = "import" ]
then 
  kc.sh -cf keycloak.conf import --file kc.json
elif [ "$1" = "export" ] 
then
  kc.sh -cf keycloak.conf export --realm duskio --file kc.json
else
  kc.sh -cf keycloak.conf start-dev
fi