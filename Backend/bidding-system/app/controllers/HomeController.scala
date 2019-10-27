package controllers

import Socket.StorageActor
import akka.actor.ActorSystem
import akka.stream.Materializer
import javax.inject._
import play.api._
import play.api.mvc._
import database.{DatabaseHandler, DatabaseUtils}
import org.mongodb.scala.bson.ObjectId
import org.mongodb.scala.bson.collection.immutable.Document
import org.mongodb.scala.model.Filters._
import play.api.libs.streams.ActorFlow

import scala.concurrent.ExecutionContext.Implicits.global

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class HomeController @Inject()(config: Configuration, cc: ControllerComponents)(implicit system: ActorSystem, mat: Materializer) extends AbstractController(cc) {

  val databaseHandler: DatabaseHandler = new DatabaseHandler(config)

  /**
   * Create an Action to render an HTML page.
   *
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/`.
   */
  def index() = Action { implicit request: Request[AnyContent] =>
    Ok("This is text send from the backend")
    //Ok(views.html.index())
  }

  def getProducts: Action[AnyContent] = Action.async { implicit request: Request[AnyContent] =>
    databaseHandler.init().flatMap(_ => {
      databaseHandler.get().map((documents: Seq[Document]) => {
        DatabaseUtils.convertToJson(documents)
      }).map(json =>
        Ok(json)
      )
    })
  }

  def getProduct(id_string : String): Action[AnyContent] = Action.async { implicit request: Request[AnyContent] =>
    val objectId = new ObjectId(id_string)
    databaseHandler.get(equal("_id", objectId)).map(( documents: Seq[Document]) => {
      DatabaseUtils.convertToJson(documents)
    } ).map( json =>
      Ok(json)
    )
  }

  def addProduct(product : String):  Action[AnyContent] = Action.async { implicit request: Request[AnyContent] =>
    databaseHandler.add(product).map(_ => {
      Ok
    })
  }

  def bidSocket = WebSocket.accept[String, String] { request =>
    ActorFlow.actorRef { out =>
      StorageActor.connect(out)
    }
  }
}

