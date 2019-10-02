package database

import java.util.concurrent.TimeUnit

import org.mongodb.scala.bson.conversions.Bson
import org.mongodb.scala.{Completed, Document, MongoClient, MongoCollection, MongoDatabase, Observer}
import play.api.Configuration

import scala.concurrent.{Await, Future}
import scala.concurrent.duration.Duration
import scala.concurrent.ExecutionContext.Implicits.global

class DatabaseHandler (config: Configuration){

  val MAX_WAIT_TIME = 10;

  var mongoClient : MongoClient = _
  var database : MongoDatabase = _
  var product_collection : MongoCollection[Document] = _

  def get(filter : Bson = null) : Future[Seq[Document]] = {
    {
      if(filter == null) {
        product_collection.find()
      }else {
        product_collection.find(filter)
      }
    }.toFuture()
  }

  def delete(filter : Bson): Unit = {
    this.connect()
    product_collection.deleteMany(filter)
    mongoClient.close()
  }

  def add(json: String): Future[Completed] = {
    val document: Document = Document(json)
    product_collection.insertOne(document).toFuture()
  }

  def init(): Future[Unit] = {
    product_collection.countDocuments().toFuture().map(doc_count => {
      if (doc_count == 0) {
        product_collection.insertMany(Array(
          Document("name" -> "magic 8 ball", "description" -> "it will tell your future", "keywords" -> "magic"),
          Document("name" -> "mysterybox", "description" -> "What could be in the box??", "keywords" -> "magic")
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
    })
//
//    val result: Future[Any] = Future {
//      val doc_count = Await.result(product_collection.countDocuments().toFuture(), Duration(MAX_WAIT_TIME, TimeUnit.SECONDS));
//      if (doc_count == 0) {
//          product_collection.insertMany(Array(
//          Document("name" -> "magic 8 ball", "description" -> "it will tell your future", "keywords" -> "magic"),
//          Document("name" -> "mysterybox", "description" -> "What could be in the box??", "keywords" -> "magic")
//        )).subscribe(new Observer[Completed]() {
//          override def onNext(result: Completed): Unit = println("Inserted product successfully")
//
//          override def onError(e: Throwable): Unit = {
//            println("error on init: " + e.getMessage)
//            mongoClient.close()
//          }
//
//          override def onComplete(): Unit = mongoClient.close()
//        })
//      }else{
//        println("Collection already contains documents")
//      }
//    }
//    result
  }

  def connect(): Unit =
  {
    try {
      this.mongoClient = MongoClient(config.get[String]("mongodb_client_location"))
      this.database = mongoClient.getDatabase(config.get[String]("mongodb_database_name"))
      this.product_collection = database.getCollection(config.get[String]("mongodb_products_collection"))
    }catch {
      case ex:Exception => ex.getMessage()
    }
  }

  def close(): Unit =
    {
      mongoClient.close()
    }


}
