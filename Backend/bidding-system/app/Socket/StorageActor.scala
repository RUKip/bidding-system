package Socket

import akka.actor._
import scala.collection.mutable

//Base idea from https://github.com/michel-medema/wacc-scala-example/tree/master/back-end/src/main/scala/rugds/wacc
object StorageActor{
  var bids: mutable.Map[String, Seq[Int]] = mutable.Map()
  var actors: mutable.Map[ActorRef, String] = mutable.Map()

  def connect(out: ActorRef) = {
    println("A user joined")
    actors += out -> "0"
    Props(new BidSocketActor(out))
  }

  def disconnect(out: ActorRef) = {
    actors -= out
    println("A user left")
  }
}


