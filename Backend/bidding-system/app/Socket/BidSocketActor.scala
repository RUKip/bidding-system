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
      println("Received message: " + json)
      val productJson = Json.parse(json)
      if((productJson \ "init").asOpt[String].isDefined) {
        this.initProduct(productJson("init").as[String])
      } else {
        val product_id = productJson("productId").as[String]
        val price = productJson("price").as[String]
        //TODO: check bid before accepting
        this.acceptBid(product_id, price)
      }
  }

  def initProduct(productId: String): Unit =
  {
    StorageActor.actors += (out -> productId)
    if (StorageActor.bids.get(productId).isEmpty) {
      StorageActor.bids(productId) = Seq(1, 2, 5) //TODO: init from db (with timestamps!)
    }
    val send = Json.stringify(DatabaseUtils.convertBidsToJson(StorageActor.bids(StorageActor.actors(out))))
    println("Initializing actor, sending: " + send)
    out ! send
  }

  def acceptBid(productId: String, price: String): Unit =
  {
    if (StorageActor.actors(out) != productId) {
      StorageActor.actors += (out -> productId)
    }
    this.bidPrice(productId, price.toInt)
    val send = Json.stringify(DatabaseUtils.convertBidsToJson(StorageActor.bids(StorageActor.actors(out))))
    println("Sending: " + send)
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
