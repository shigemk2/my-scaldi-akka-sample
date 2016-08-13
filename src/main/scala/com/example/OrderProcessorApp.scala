package com.example

import scala.language.postfixOps

import akka.actor.{ActorSystem, Inbox}
import scaldi.akka.AkkaInjectable
import scaldi.Module

import scala.concurrent.duration._

class OrderModule extends Module {
  binding toProvider new Receptionist
  binding toProvider new OrderProcessor
  binding toProvider new PriceCalculator
}

class AkkaModule extends Module {
    bind [ActorSystem] to ActorSystem("ScaldiModule") destroyWith (_.shutdown())
}

object OrderProcessorApp extends App with AkkaInjectable {
  import Messages._

  val userModule = new Module {
    bind [UserService] to new SimpleUserService
  }
  implicit val appModule = new OrderModule :: userModule :: new AkkaModule
  implicit val system = inject [ActorSystem]

  val receptionist = injectActorRef [Receptionist]
  val inbox = Inbox.create(system)

  inbox send (receptionist, PlaceOrder("test", 1, 1234))

  val OrderProcessingStarted(_) = inbox.receive(5 seconds)
  val OrderProcessed(orderNumber, grossAmount) = inbox.receive(5 seconds)

  println(s"Order created. $orderNumber, Gross Amount: $grossAmount")
  appModule.destroy()
}
