package database

import org.mongodb.scala.{Document, MongoClient, MongoCollection, MongoDatabase}

class DatabaseHandler {
  val mongoClient : MongoClient = MongoClient("mongodb://mongo")
  val database : MongoDatabase = mongoClient.getDatabase("bidding-system")
  val product_collection : MongoCollection[Document] = database.getCollection("products");

  def handle(){

  }

  def init(){
    if(product_collection.estimatedDocumentCount() == 0) {
      product_collection.insertMany(Array(
        Document("_id" -> 1, "name" -> "magic 8 ball", "description" -> "it will tell your future"),
        Document("_id" -> 2, "name" -> "mysterybox", "description" -> "What could be in the box??")
      ))
    }
  }
}
