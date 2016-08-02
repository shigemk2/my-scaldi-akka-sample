package com.example

import scaldi.Injector
import scaldi.akka.AkkaInjectable

class Receptionist (implicit inj: Injector) extends Actor with AkkaInjectable {
  val userService inject [UserService]

  val orderProcessorProps = injectActorProps [OrderProcessor]
  val priceCalculator = injectActorRef [PriceCalculator]

  def receive = {
    case PlaceOrder(userName, itemId, netAmount) =>
      val processor = context.actorRef(orderProcessorProps)
      // ..
  }
}