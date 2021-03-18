#!/bin/bash

# Installs JAR files to make them available to Maven

GROUP_ID=eu.ttc2020.ocl

mvn install:install-file \
    -DgroupId="$GROUP_ID" \
    -DartifactId=dm2schema \
    -Dversion=1.0.0-SNAPSHOT \
    -Dpackaging=jar \
    -Dfile=lib/dm2schema.jar
	
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

mvn install:install-file \
    -DgroupId="$GROUP_ID" \
    -DartifactId=sql-metamodel \
    -Dversion=1.0.0-SNAPSHOT \
    -Dpackaging=jar \
    -Dfile=lib/sql.jar
	
mvn install:install-file \
    -DgroupId="$GROUP_ID" \
    -DartifactId=javax-inject \
    -Dversion=1.0.0-SNAPSHOT \
    -Dpackaging=jar \
    -Dfile=lib/javax.inject_1.0.0.v20091030.jar
	
mvn install:install-file \
    -DgroupId="$GROUP_ID" \
    -DartifactId=core-contenttype \
    -Dversion=1.0.0-SNAPSHOT \
    -Dpackaging=jar \
    -Dfile=lib/org.eclipse.core.contenttype_3.7.300.v20190215-2048.jar
	
mvn install:install-file \
    -DgroupId="$GROUP_ID" \
    -DartifactId=core-jobs \
    -Dversion=1.0.0-SNAPSHOT \
    -Dpackaging=jar \
    -Dfile=lib/org.eclipse.core.jobs_3.10.300.v20190215-2048.jar
	
mvn install:install-file \
    -DgroupId="$GROUP_ID" \
    -DartifactId=core-runtime \
    -Dversion=1.0.0-SNAPSHOT \
    -Dpackaging=jar \
    -Dfile=lib/org.eclipse.core.runtime_3.15.200.v20190301-1641.jar

mvn install:install-file \
    -DgroupId="$GROUP_ID" \
    -DartifactId=emf-common \
    -Dversion=1.0.0-SNAPSHOT \
    -Dpackaging=jar \
    -Dfile=lib/org.eclipse.emf.common_2.17.0.v20190920-0401.jar
	
mvn install:install-file \
    -DgroupId="$GROUP_ID" \
    -DartifactId=emf-ecore-xmi \
    -Dversion=1.0.0-SNAPSHOT \
    -Dpackaging=jar \
    -Dfile=lib/org.eclipse.emf.ecore.xmi_2.16.0.v20190528-0725.jar

mvn install:install-file \
    -DgroupId="$GROUP_ID" \
    -DartifactId=emf-ecore \
    -Dversion=1.0.0-SNAPSHOT \
    -Dpackaging=jar \
    -Dfile=lib/org.eclipse.emf.ecore_2.20.0.v20190920-0401.jar

mvn install:install-file \
    -DgroupId="$GROUP_ID" \
    -DartifactId=equinox-app \
    -Dversion=1.0.0-SNAPSHOT \
    -Dpackaging=jar \
    -Dfile=lib/org.eclipse.equinox.app_1.4.100.v20190215-2139.jar

mvn install:install-file \
    -DgroupId="$GROUP_ID" \
    -DartifactId=equinox-common \
    -Dversion=1.0.0-SNAPSHOT \
    -Dpackaging=jar \
    -Dfile=lib/org.eclipse.equinox.common_3.10.300.v20190218-2100.jar

mvn install:install-file \
    -DgroupId="$GROUP_ID" \
    -DartifactId=equinox-preferences \
    -Dversion=1.0.0-SNAPSHOT \
    -Dpackaging=jar \
    -Dfile=lib/org.eclipse.equinox.preferences_3.7.300.v20190218-2100.jar

mvn install:install-file \
    -DgroupId="$GROUP_ID" \
    -DartifactId=osgi \
    -Dversion=1.0.0-SNAPSHOT \
    -Dpackaging=jar \
    -Dfile=lib/org.eclipse.osgi_3.13.300.v20190218-1622.jar

