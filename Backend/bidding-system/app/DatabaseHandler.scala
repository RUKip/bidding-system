import org.mongodb.scala.{MongoClient, MongoCollection, MongoDatabase}

import scala.xml.Document

class DatabaseHandler {
  val mongoClient : MongoClient = MongoClient("") //TODO: set from config file
  val database : MongoDatabase = mongoClient.getDatabase("bidding-system")
  val product_collection : MongoCollection[Document] = database.getCollection("products");

  def handle(){

  }
}
