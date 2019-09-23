package database

import org.mongodb.scala.bson.conversions.Bson
import org.mongodb.scala.{Document, MongoClient, MongoCollection, MongoDatabase, Observer}
import play.api.libs.json.{JsValue, Json}

import scala.io.Source

class DatabaseHandler(){

  private val bufferedSource = Source.fromFile("../conf/settings.json")
  private val settings = Json.parse(bufferedSource.getLines.mkString)
  bufferedSource.close

  val mongoClient : MongoClient = MongoClient((settings("mongodb_client")).as[String])
  val database : MongoDatabase = mongoClient.getDatabase((settings("mongodb_database")).as[String])
  val product_collection : MongoCollection[Document] = database.getCollection((settings("mongodb_collection_products")).as[String])

  def get(filter : Bson) : JsValue = {
    val result = product_collection.find(filter)
    Json.parse(result.toString)
  }

  def delete(filter : Bson){
    product_collection.deleteMany(filter)
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
