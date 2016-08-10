package com.example

class OrderModule extends Module {
  binding toProvider new Receptionist
  binding toProvider new OrderProcessor
  binding toProvider new PriceCalculator
}

object AkkaModule extends Module {
    bind [ActorSystem] to ActorSystem("ScaldiModule") destroyWith (_.shutdown())
}

object OrderProcessorApp extends Module {
  import Messages._

  val userModule = new Module {
    bind [UserService] to new SimpleUserService
  }
  implicit val appModule = new OrderModule :: userModule :: new AkkaModule

  implicit val system = inject [ActorSystem]

  val receptionist = injectActorRef [Receptionist]
  val inbox = Inbox.create(system)

  inbox send (receptionist, PriceOrder("test", 1, 1234))

  val OrderProcessingStarted(_) = inbox.receive(5 seconds)
  val OrderProcessed(orderNumber, grossAmount) = inbox.receive(5 seconds)

  println(s"Order created. $orderNumber, Gross Amount: $grossAmount")
  appModule.destroy
}
