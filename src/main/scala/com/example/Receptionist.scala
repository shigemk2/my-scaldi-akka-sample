package com.example

class Receptionist {
}

object Messages {
  case class PlaceOrder(userName: String, itemId: Long, netAmount: Int)
  case class ProcessOrder(user: User, itemId: Long, netAmount: Int)
  case class CalculatePrice(netAmount: Int)
  case object CancelProcessing

  case class OrderProcessingFailed(reason: String)
  case class OrderProcessed(orderNumber: String, grossAmount: Int)
  case class OrderProcessingStarted(processor: ActorRef)
  case class GrossPriceCalculated(netPrice: Int, grossPrice: Int)
}
