package controllers

import javax.inject._
import play.api._
import play.api.mvc._
import play.api.libs.json._
import database.{DatabaseHandler, DatabaseUtils}

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
//    val json : JsValue = Json.obj(
//       "products" -> Json.arr(
//         Json.obj(
//           "id"-> 1,
//           "name" -> "mysterybox",
//           "description" -> "What could be in the box??"
//         ),
//         Json.obj(
//           "id"-> 2,
//           "name" -> "magic 8 ball",
//           "description" -> "it will tell your future"
//        ))
//       )
    val databaseHandler: DatabaseHandler = new DatabaseHandler(config);
    databaseHandler.init()
    val json = databaseHandler.get(DatabaseUtils.createAndFilter(Map("keywords" -> "magic")))
    Ok(json)
  }

  def getProduct(id_string : String): Action[AnyContent] = Action { implicit request: Request[AnyContent] =>
    var json: JsValue = null
//    val id = id_string.toInt;
//    if(id == 1) {
//      json = Json.obj(
//        "id" -> id,
//        "name" -> "mysterybox",
//        "description" -> "What could be in the box??"
//      )
//    }else{
//      json = Json.obj(
//        "id"-> id,
//        "name" -> "magic 8 ball",
//        "description" -> "it will tell your future"
//      )
//    }
    val databaseHandler: DatabaseHandler = new DatabaseHandler(config);
    databaseHandler.init()
    json = databaseHandler.get(DatabaseUtils.createAndFilter(Map("id" -> id_string)))
    Ok(json)
  }
}
