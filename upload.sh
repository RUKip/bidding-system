#!/bin/bash

cd Frontend/
docker build -t bidding-frontend -f Docker/Dockerfile .
docker tag bidding-frontend:latest grkip/bidding-frontend:latest
docker push grkip/bidding-frontend:latest

cd ../Backend/bidding-system
sbt docker:publishLocal
docker tag bidding-system:1.0-SNAPSHOT grkip/bidding-system:latest
docker push grkip/bidding-system:latest
