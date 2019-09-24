package database

import java.util.concurrent.TimeUnit

import org.mongodb.scala.bson.conversions.Bson
import org.mongodb.scala.{Completed, Document, FindObservable, MongoClient, MongoCollection, MongoDatabase, Observer}
import play.api.libs.json.{JsValue, Json}
import play.api.Configuration

import scala.concurrent.{Await, Future}
import scala.concurrent.duration.Duration
import scala.concurrent.ExecutionContext.Implicits.global

class DatabaseHandler (config: Configuration){

  val MAX_WAIT_TIME = 10;

  var mongoClient : MongoClient = _
  var database : MongoDatabase = _
  var product_collection : MongoCollection[Document] = _

  def get(filter : Bson) : JsValue = {
    this.connect()

    //TODO: filter does not work yet correctly

    var json_string : String = "["
    val result: FindObservable[Document] = {
      if(filter == null) {
        product_collection.find()
      }else {
        product_collection.find(filter)
      }
    }

    //Wait for response to return
    val documents = Await.result(result.toFuture(), Duration(MAX_WAIT_TIME, TimeUnit.SECONDS))
    println(documents) //TODO: remove
    for (doc <- documents) {
      if (json_string == "[") {
        json_string += doc.toJson()
      }else {
        json_string += "," + doc.toJson()
      }
    }
    json_string += "]"

    mongoClient.close()
    println("Returning json: " + json_string)
    Json.parse(json_string)
  }

  def delete(filter : Bson): Unit = {
    this.connect()
    product_collection.deleteMany(filter)
    mongoClient.close()
  }

  def init(): Unit = {
    val future: Future[Any] = Future {
      this.connect()
      println(config.get[String]("mongodb_client_location"))

      val doc_count = Await.result(product_collection.countDocuments().toFuture(), Duration(MAX_WAIT_TIME, TimeUnit.SECONDS));
      println(doc_count)
      if (doc_count == 0) {
          product_collection.insertMany(Array(
          Document("_id" -> 1, "name" -> "magic 8 ball", "description" -> "it will tell your future", "keywords" -> "magic"),
          Document("_id" -> 2, "name" -> "mysterybox", "description" -> "What could be in the box??", "keywords" -> "magic")
        )).subscribe(new Observer[Completed]() {
          override def onNext(result: Completed): Unit = println("Inserted product successfully")

          override def onError(e: Throwable): Unit = {
            println("error on init: " + e.getMessage)
            mongoClient.close()
          }

          override def onComplete(): Unit = mongoClient.close()
        })
      }else{
        println("Collection already contains documents")
      }
    }
    val result = Await.result(future, Duration(MAX_WAIT_TIME, TimeUnit.SECONDS));
    mongoClient.close()
    result
  }

  private def connect(): Unit =
  {
    try {
      this.mongoClient = MongoClient(config.get[String]("mongodb_client_location"))
      this.database = mongoClient.getDatabase(config.get[String]("mongodb_database_name"))
      this.product_collection = database.getCollection(config.get[String]("mongodb_products_collection"))
    }catch {
      case ex:Exception => ex.getMessage()
    }
  }
}
