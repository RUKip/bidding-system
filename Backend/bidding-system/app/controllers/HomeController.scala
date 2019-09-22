package controllers

import javax.inject._
import play.api._
import play.api.mvc._
import play.api.libs.json._

import scala.util.parsing.json.{JSONArray, JSONObject}

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class HomeController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

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

//  def mongocall(): Action[AnyContent] = Action { implicit request: Request[AnyContent] =>
//
//  }

  def test(): Action[AnyContent] = Action { implicit request: Request[AnyContent] =>
    val json : JsValue = Json.obj(
       "products" -> Json.arr(
         Json.obj(
           "id"-> 0,
           "name" -> "mysterybox",
           "description" -> "What could be in the box??"
         ))
       )
    Ok(json)
  }
}
