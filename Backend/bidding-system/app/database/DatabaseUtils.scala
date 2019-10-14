package database

import org.mongodb.scala.Document
import org.mongodb.scala.bson.conversions.Bson
import org.mongodb.scala.model.Filters.{and, equal}
import play.api.libs.json.{JsValue, Json}

object DatabaseUtils {

  def convertToJson(documents: Seq[Document]): JsValue =
  {
    Json.parse(
     documents.map( doc => {
        doc.toJson()
      }).mkString("[", ",", "]")
    )
  }

  def createAndFilter(options: Map[String, Any]): Bson = {
    var filter : Bson = null
    options foreach { case (key, value) =>
      filter = and(filter, equal(key, value))
    }
    filter
  }
}
