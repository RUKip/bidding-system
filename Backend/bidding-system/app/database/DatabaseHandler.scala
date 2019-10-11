package database

import org.mongodb.scala.bson.conversions.Bson
import org.mongodb.scala.{Completed, Document, MongoClient, MongoCollection, MongoDatabase, Observer}
import play.api.Configuration

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

class DatabaseHandler (config: Configuration){

  val MAX_WAIT_TIME = 10;

  val mongoClient : MongoClient = MongoClient(config.get[String]("mongodb_client_location"))
  val database : MongoDatabase = mongoClient.getDatabase(config.get[String]("mongodb_database_name"))
  val product_collection : MongoCollection[Document] = database.getCollection(config.get[String]("mongodb_products_collection"))

  def get(filter : Bson) : Future[Seq[Document]] = {
        product_collection.find(filter).toFuture()
  }

  def get() : Future[Seq[Document]] = {
    product_collection.find().toFuture()
  }

  def delete(filter : Bson): Unit = {
    product_collection.deleteMany(filter)
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
          }

          override def onComplete(): Unit = {}
        })
      }else{
        println("Collection already contains documents")
      }
    })
  }
}
