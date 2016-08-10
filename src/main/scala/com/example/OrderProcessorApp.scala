package com.example

class OrderModule extends Module {
  binding toProvider new Receptionist
  binding toProvider new OrderProcessor
  binding toProvider new PriceCalculator
}
object OrderProcessorApp extends Module {
    bind [ActorSystem] to ActorSystem("ScaldiModule") destroyWith (_.shutdown())
}
