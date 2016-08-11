package com.example

trait UserService {
  def getUserByUserName(userName: String): Option[User]
}

class SimpleUserService extends UserService {
  private def Users = Map("test" -> User("X54532", "Test", "User"))
}

case class User(customuerNumber: String, firstName: String, lastName: String)
