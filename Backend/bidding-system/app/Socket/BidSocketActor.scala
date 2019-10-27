package Socket

import akka.actor._
import database.DatabaseUtils
import play.api.libs.json.Json

import scala.collection.mutable

object BidSocketActor {
  def props(out: ActorRef) = Props(new BidSocketActor(out))
  var bids: mutable.Map[String, Seq[Int]] = mutable.Map()
}

class BidSocketActor(out: ActorRef) extends Actor {

  var product_id = "0"

  def receive: PartialFunction[Any, Unit] = {
    case json: String =>
      val productJson = Json.parse(json)
      val product_id = productJson("productId").as[String]
      val price = productJson("price").as[String]
      if(this.product_id != product_id) {
          this.product_id = product_id
      }
      if(BidSocketActor.bids.get(product_id).isEmpty) {
        BidSocketActor.bids(product_id) = Seq(1, 2, 5) //TODO: init from db
      }
      this.bidPrice(product_id, price.toInt)
      val send = Json.stringify(DatabaseUtils.convertBidsToJson(BidSocketActor.bids(this.product_id)))
      println("Sending: " + send) //TODO: debug/log remove later
      out ! (send)
  }

  def bidPrice(product_id : String, price : Int) = {
    BidSocketActor.bids(product_id) = BidSocketActor.bids(product_id) :+ price
    //TODO: insert bid in db
    //TODO: insert bid in mq
  }

  def fromMQ = {
      //TODO: read from mq and insert bids in out?
  }
}
