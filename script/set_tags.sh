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
        pastVersion = version
        (( version++ ))
        echo "$version" > versionTracker.txt

	    # Add tag and push to master.
	    git tag -a v${version} -m "Travis pushing  ${version} a tag."
	    git push origin --tags
	    git fetch origin

	    git add script/versionTracker.txt
	    git commit -m "update versionTracker.txt from ${pastVersion} to ${version} "
	    git push


	    echo -e "Done magic with tags.\n"
	fi
  fi
fi
