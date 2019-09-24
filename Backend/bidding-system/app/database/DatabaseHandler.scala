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

    val result : FindObservable[Document] = product_collection.find(filter)
    val documents = Await.result(result.toFuture(), Duration(MAX_WAIT_TIME, TimeUnit.SECONDS))

    mongoClient.close()
    if (documents != null && documents.nonEmpty && documents.head.nonEmpty) {
      println(documents.toString())
      Json.parse(documents.toString())
    }else{
      println("documents was empty")
      Json.parse("{}")
    }
  }

  def delete(filter : Bson): Unit = {
    this.connect()
    product_collection.deleteMany(filter)
    mongoClient.close()
  }

  def init(): Unit = {
    try {
      val future: Future[Any] = Future {
        this.connect()
        println(config.get[String]("mongodb_client_location"))
          if (product_collection.countDocuments() == 0) {
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
          }
      }
      Await.result(future, Duration(MAX_WAIT_TIME, TimeUnit.SECONDS));
    } catch {
      case ex: Exception => ex.getMessage()
    }
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
