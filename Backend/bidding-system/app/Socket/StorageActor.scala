package Socket

import akka.actor._
import scala.collection.mutable

//Base idea from https://github.com/michel-medema/wacc-scala-example/tree/master/back-end/src/main/scala/rugds/wacc
object StorageActor{
  var bids: mutable.Map[String, Seq[Int]] = mutable.Map()
  var actors: mutable.Map[ActorRef, Props] = mutable.Map()

  def connect(out: ActorRef) = {
    println("A user joined")
    val props = Props(new BidSocketActor(out))
    actors += out -> props
    props
  }

  def disconnect(actorRef: ActorRef) = {
    actors -= actorRef
    println("A user left")
  }
}


