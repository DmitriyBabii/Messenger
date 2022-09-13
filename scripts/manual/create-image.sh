#!/bin/bash

IMAGE_NAME=TestTask

mvn clean install -DskipTests=true

docker build -t $IMAGE_NAME .

