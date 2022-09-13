#!/bin/bash

IMAGE_NAME=localhost:8089
CONTAINER_NAME="TestTask"

docker container stop $CONTAINER_NAME
docker container rm $CONTAINER_NAME
docker run --name $CONTAINER_NAME -p 8089:8089 -d $IMAGE_NAME