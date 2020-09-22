#! /bin/bash

mvn clean package site

mkdir -p doc; rm -rf doc/site; cp -a target/{site,generated-docs} doc
mkdir -p artifact; cp target/pessoa-fisica-service-1.0.0.jar artifact


