package database

import org.mongodb.scala.Document
import org.mongodb.scala.bson.conversions.Bson
import org.mongodb.scala.model.Filters.{and, equal}
import play.api.libs.json.{JsValue, Json}

object DatabaseUtils {

  def convertToJson(documents: Seq[Document]): JsValue =
  {
    var json_string : String = "["
    documents.foreach{ doc: Document =>
      if (json_string == "[") {
        json_string += doc.toJson()
      }else {
        json_string += "," + doc.toJson()
      }
    }
    json_string += "]"
    Json.parse(json_string)
  }

  def createAndFilter(options: Map[String, Any]): Bson = {
    var filter : Bson = null
    options foreach { case (key, value) =>
      filter = and(filter, equal(key, value))
    }
    filter
  }
}
