sh.addShard("rs0/mongo:27017")
sh.enableSharding("bidding-system")
sh.shardCollection("bidding-system.products", { _id : "hashed" } )
