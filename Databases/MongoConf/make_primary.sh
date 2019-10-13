#!/bin/bash
mongo
rs.initiate( {
   _id : "conf0",
   members: [
      { _id: 0, host: "mongoconf1:27017" },
      { _id: 1, host: "mongoconf2:27017" },
      { _id: 2, host: "mongoconf3:27017" }
   ]
})
