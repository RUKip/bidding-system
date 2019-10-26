package Socket

import akka.actor._
import database.DatabaseUtils
import play.api.libs.json.Json

object BidSocketActor {
  def props(out: ActorRef) = Props(new BidSocketActor(out))
}

class BidSocketActor(out: ActorRef) extends Actor {

  var bids: Seq[Int] = Seq()
  var product_id = "0"

  def receive: PartialFunction[Any, Unit] = {
    case json: String =>
      val productJson = Json.parse(json)
      val product_id = productJson("productId").as[String]
      val price = productJson("price").as[String]
      if(product_id != this.product_id){
        this.product_id = product_id
        this.bids = Seq(1, 2, 5)//TODO: init from db
      }
      this.bidPrice(product_id, price.toInt)
      val send = Json.stringify(DatabaseUtils.convertBidsToJson(this.bids))
      println("Sending: " + send) //TODO: debug/log remove later
      out ! (send)
  }

  def bidPrice(product_id : String, price : Int) = {
    this.bids = this.bids :+ price
    //TODO: insert bid in db
    //TODO: insert bid in mq
  }

  def fromMQ = {
      //TODO: read from mq and insert bids in out?
  }
}
