package com.example

import java.math.RoundingMode
import java.util.UUID

import scaldi.Injector
import scaldi.akka.AkkaInjectable

class OrderProcessor(implicit inj: Injector) extends Actor with AkkaInjectable {
  import Messages._

  val priceCalculator = injectActorRef [PriceCalculator]

  def receive = idle

  val idle: Receive = {
    case orderInfo @ ProcessOrder(user: User, itemId: Long, netAmount: Int) =>
      println(s"Processing order for user $user.")

      priceCalculator ! CalculatePrice(netAmount)

      context beccome workingHard(orderInfo, sender)
  }

  def workingHard(orderInfo: ProcessOrder, reportTo: ActorRef): Receive = {
    case CancelProcessing =>
      reportTo ! OrderProcesingFailed(UUID.randomUUID().toString, grossPrice)
      self ! PoisonPill
  }
}

class PriceCalculator extends Actor {
  import Messages._

  def receive = {
    case CalculatePrice(netAmount) =>
      val grossCent = (netAmount * BigDecimal("1.19")).setScale(0, RoundingMode.HALF_UP).toIntExact
      sendor ! GrossPriceCalculated(netAmount, grossCent)
  }
}
