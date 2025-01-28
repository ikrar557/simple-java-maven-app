#!/usr/bin/env bash

ssh -o StrictHostKeyChecking=no ${EC2_HOST} "
    docker stop java-app
    docker rm java-app
"