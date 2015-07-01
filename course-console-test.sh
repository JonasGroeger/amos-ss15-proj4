#!/bin/bash

usage()
{
    echo "You have to provide a valid JSESSIONID. Preferably using the browser."
    echo -e "\tUsage: ./course-console-test.sh JSESSIONID"
}

###############################################################################

if [ $# -le 0 ]
then
    usage
    exit 1
fi

curl 'https://osr-amos.cs.fau.de/ss15/console/?3-1.ILinkListener-download~properties' -H "Cookie: JSESSIONID=$1"
