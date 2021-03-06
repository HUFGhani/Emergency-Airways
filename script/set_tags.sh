#!/usr/bin/env bash
 BRANCH="master"

 if [ "$TRAVIS_BRANCH" = "$BRANCH" ]; then
   if [ "$TRAVIS_PULL_REQUEST" = false ]; then
   	if [ -z "$TRAVIS_TAG" ]; then
	    echo -e "Starting to tag commit.\n"

	    git config --global user.email "travis@travis-ci.org"
	    git config --global user.name "Travis"

        version=$(<script/versionTracker.txt)
        echo "$version"
        pastVersion=$(<script/versionTracker.txt)
        (( version++ ))
        echo "$version" > versionTracker.txt

        mv app/build/outputs/apk/debug/app-debug.apk app/build/outputs/apk/debug/Emergency-Airways.apk

	    # Add tag and push to master.
	    git tag -a v${version} -m "Travis pushing  ${version} a tag."
	    git push origin --tags
	    git fetch origin


	    git add .
	    git commit -m "update versionTracker.txt from ${pastVersion} to ${version} "
	    git push origin HEAD:master


	    echo -e "Done magic with tags.\n"
 	fi
   fi
 fi
