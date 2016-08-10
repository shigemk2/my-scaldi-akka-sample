package com.example

object OrderProcessorApp extends Module {
    bind [ActorSystem] to ActorSystem("ScaldiModule") destroyWith (_.shutdown())
}
