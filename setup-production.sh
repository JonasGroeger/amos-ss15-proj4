#!/bin/bash

usage()
{
    echo "You have to provide a database name, username and password."
	echo -e "\tUsage: ./setup-production.sh DATABASE USERNAME PASSWORD"
}

###############################################################################

SAMPLE_FILE="src/main/resources/config/application-production.yml.sample"
PROD_FILE="src/main/resources/config/application-production.yml"

if [ -f "$PROD_FILE" ]
then
	echo "Script has already been executed."
	exit 1
fi

if [ $# -le 2 ]
then
    usage
    exit 1
fi

cp "$SAMPLE_FILE" "$PROD_FILE"
sed -i -e "s/<PRODUCTION_DATABASE>/$1/g" "$PROD_FILE"
sed -i -e "s/<PRODUCTION_USERNAME>/$2/g" "$PROD_FILE"
sed -i -e "s/<PRODUCTION_PASSWORD>/$3/g" "$PROD_FILE"
sed -i -e "/^#/d" "$PROD_FILE"
sed -i -e "1 i\# This is the production specific configuration using the 'production' profile." "$PROD_FILE"