package Socket

import akka.actor._
import play.libs.Json

object BidSocketActor {
  def props(out: ActorRef) = Props(new BidSocketActor(out))
}

class BidSocketActor(out: ActorRef) extends Actor {

  var bids: Seq[Int] = Seq()
  var product_id = "0"

  def receive: PartialFunction[Any, Unit] = {
    case json: String =>
      val productjson = Json.parse(json)
      val product_id = productjson.get("productId").toString
      val price = productjson.get("price").intValue()
      if(product_id != this.product_id){
        println("Setting bids")
        this.product_id = product_id
        this.bids = Seq(1, 2, 5)//TODO: init from db
      }
      println(Json.toJson(bids))
      this.bidPrice(product_id, price)
      val send = Json.stringify(Json.toJson(bids))
      println("Sending: " + send)
      out ! (send)
  }

  def bidPrice(product_id : String, price : Int) = {
    bids :+ price
    //TODO: insert bid in db
    //TODO: insert bid in mq
  }

  def fromMQ = {
      //TODO: read from mq and insert bids in out?
  }
}
