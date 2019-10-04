#!/bin/bash

# Make sure 3 mongo instances are available
for rs in mongo mongo-2 mongo-3;do
  mongo --host $rs --eval 'db'
  if [ $? -ne 0 ]; then
    exit 1
  fi
done

# Connect to mongo and configure replica set if not done
status=$(mongo --host mongo --quiet --eval 'rs.status().members.length')

if [ $? -ne 0 ]; then
  # Replicaset not yet configured
  mongo --host mongo --eval 'rs.initiate({ _id: "rs0", version: 1, members: [ { _id: 0, host : "mongo:27017" }, { _id: 1, host : "mongo-2:27017" }, { _id: 2, host : "mongo-3:27017" } ] })';
fi
