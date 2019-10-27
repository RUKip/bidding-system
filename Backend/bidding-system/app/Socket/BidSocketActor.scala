package Socket

import akka.actor._
import database.DatabaseUtils
import play.api.libs.json.Json

object BidSocketActor {
  def props(out: ActorRef) = Props(new BidSocketActor(out))
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
      if(StorageActor.bids.get(product_id).isEmpty) {
        StorageActor.bids(product_id) = Seq(1, 2, 5) //TODO: init from db
      }
      this.bidPrice(product_id, price.toInt)
      val send = Json.stringify(DatabaseUtils.convertBidsToJson(StorageActor.bids(this.product_id)))
      println("Sending: " + send) //TODO: debug/log remove later
      broadcast(send)
  }

  def bidPrice(product_id : String, price : Int) = {
    StorageActor.bids(product_id) = StorageActor.bids(product_id) :+ price
    //TODO: insert bid in db
    //TODO: insert bid in mq
  }

  def fromMQ = {
      //TODO: read from mq and insert bids in out?
  }

  def broadcast(bids: String): Unit = StorageActor.actors.keys.foreach(_ ! bids)
}
