#!/bin/bash

echo "prepare rs initiating"

check_db_status() {
  mongo1=$(mongo --host mongoconf1 --port 27017 --eval "db.stats().ok" | tail -n1 | grep -E '(^|\s)1($|\s)')
  mongo2=$(mongo --host mongoconf2 --port 27017 --eval "db.stats().ok" | tail -n1 | grep -E '(^|\s)1($|\s)')
  mongo3=$(mongo --host mongoconf3 --port 27017 --eval "db.stats().ok" | tail -n1 | grep -E '(^|\s)1($|\s)')
  if [[ $mongo1 == 1 ]] && [[ $mongo2 == 1 ]] && [[ $mongo3 == 1 ]]; then
    init_rs
  else
    check_db_status
  fi
}

init_rs() {
  ret=$(mongo --host mongoconf1 --port 27017 --eval "rs.initiate({ _id: 'conf0', configsvr: true, members: [{ _id: 0, host: 'mongoconf1:27017' }, { _id: 1, host: 'mongoconf2:27017' }, { _id: 2, host: 'mongoconf3:27017' } ] })" > /dev/null 2>&1)
}

check_db_status > /dev/null 2>&1

echo "rs initiating finished"
exit 0
