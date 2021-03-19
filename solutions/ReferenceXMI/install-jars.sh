#!/bin/bash

# Installs JAR files to make them available to Maven

GROUP_ID=eu.ttc2020.ocl

mvn install:install-file \
    -DgroupId="$GROUP_ID" \
    -DartifactId=ocl2psql \
    -Dversion=1.0.0-SNAPSHOT \
    -Dpackaging=jar \
    -Dfile=lib/OCL2PSQL.jar
	
mvn install:install-file \
    -DgroupId="$GROUP_ID" \
    -DartifactId=ocl-java \
    -Dversion=1.0.0-SNAPSHOT \
    -Dpackaging=jar \
    -Dfile=lib/jocl.jar
	
mvn install:install-file \
    -DgroupId="$GROUP_ID" \
    -DartifactId=ocl-metamodel \
    -Dversion=1.0.0-SNAPSHOT \
    -Dpackaging=jar \
    -Dfile=lib/ocl.jar
	
mvn install:install-file \
    -DgroupId="$GROUP_ID" \
    -DartifactId=sql-metamodel \
    -Dversion=1.0.0-SNAPSHOT \
    -Dpackaging=jar \
    -Dfile=lib/sql.jar