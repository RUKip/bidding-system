package database

import org.mongodb.scala.bson.conversions.Bson
import org.mongodb.scala.model.Filters.{and, equal}

object DatabaseUtils {

  def createAndFilter(options: Map[String, Any]): Bson = {
    var filter : Bson = null
    options foreach { case (key, value) =>
      filter = and(filter, equal(key, value))
    }
    filter
  }
}
