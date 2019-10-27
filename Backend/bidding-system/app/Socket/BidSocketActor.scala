package Socket

import akka.actor._
import database.DatabaseUtils
import play.api.libs.json.Json

object BidSocketActor {
  def props(out: ActorRef) = Props(new BidSocketActor(out))
}

class BidSocketActor(out: ActorRef) extends Actor {

  def receive: PartialFunction[Any, Unit] = {
    case json: String =>
      val productJson = Json.parse(json)
      val product_id = productJson("productId").as[String]
      val price = productJson("price").as[String]
      if(StorageActor.actors(out) != product_id) {
        StorageActor.actors += (out -> product_id)
      }
      if(StorageActor.bids.get(product_id).isEmpty) {
        StorageActor.bids(product_id) = Seq(1, 2, 5) //TODO: init from db (with timestamps!)
      }
      this.bidPrice(product_id, price.toInt)
      val send = Json.stringify(DatabaseUtils.convertBidsToJson(StorageActor.bids(StorageActor.actors(out))))
      println("Sending: " + send) //TODO: debug/log remove later
      broadcast(send)
  }

  def bidPrice(product_id : String, price : Int) = {
    StorageActor.bids(product_id) = StorageActor.bids(product_id) :+ price
    //TODO: insert bid in db (with timestamp!)
    //TODO: insert bid in mq
  }

  def fromMQ = {
      //TODO: read from mq and insert bids in out?
  }

  def broadcast(bids: String): Unit = StorageActor.actors.filterKeys(actorRef => StorageActor.actors(out) == StorageActor.actors(actorRef)).keys.foreach(_ ! bids)
}
