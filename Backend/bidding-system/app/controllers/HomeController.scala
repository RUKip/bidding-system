package controllers

import javax.inject._
import play.api._
import play.api.mvc._
import play.api.libs.json._
import database.{DatabaseHandler, DatabaseUtils}
import org.mongodb.scala.model.Filters._

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class HomeController @Inject()(config: Configuration, cc: ControllerComponents) extends AbstractController(cc) {

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

  def getProducts(): Action[AnyContent] = Action { implicit request: Request[AnyContent] =>
    val databaseHandler: DatabaseHandler = new DatabaseHandler(config);
    databaseHandler.init()
    val json = databaseHandler.get(null)
    Ok(json)
  }

  def getProduct(id_string : String): Action[AnyContent] = Action { implicit request: Request[AnyContent] =>
    var json: JsValue = null
    val databaseHandler: DatabaseHandler = new DatabaseHandler(config);
    json = databaseHandler.get(equal("_id", id_string.toInt))
    Ok(json)
  }
}
