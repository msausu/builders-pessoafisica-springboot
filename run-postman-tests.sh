#! /bin/bash

find target/generated-snippets/ \
  -mindepth 1                   \
  -maxdepth 1                   \
  -type d                       \
  -print                        \
    | while read D
      do 
        mkdir -p tst/$(basename ${D})
        restdocs-to-postman --input ${D} --export-format postman --determine-folder secondLastFolder --output tst/$(basename ${D})/$(basename ${D}).json
      done

java -jar target/pessoa-fisica-service-1.0.0.jar &
PID=${!}

sleep 12

pushd tst

find -mindepth 1 -maxdepth 1 -type d | while read D; do newman run $(basename ${D})/$(basename ${D}).json; done

popd

kill -KILL ${PID}



