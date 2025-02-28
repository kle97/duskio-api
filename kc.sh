#!/bin/bash
export KEYCLOAK_ADMIN=admin
export KEYCLOAK_ADMIN_PASSWORD=123456
export JAVA_OPTS=-Dh2.bindAddress=localhost

if [ "$1" = "import" ]
then 
  kc.sh -cf keycloak.conf import --file kc.json --override true
elif [ "$1" = "export" ] 
then
  kc.sh -cf keycloak.conf export --realm duskio --file kc.json
elif [ "$1" = "test" ]
then
  file="duskio.mv.db"
  if [ -f "$file" ]
  then
    rm "$file"
    echo "Removed database file: $file"
  fi
  kc.sh -cf keycloak.conf start-dev --import-realm --optimized
else
  kc.sh -cf keycloak.conf start-dev --import-realm --optimized
fi