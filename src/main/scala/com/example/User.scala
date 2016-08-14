package com.example

import scaldi.Injectable

trait UserService {
  def getUserByUserName(userName: String): Option[User]
}

class SimpleUserService extends UserService {
  private def Users = Map("test" -> User("X54532", "Test", "User"))

  override def getUserByUserName(userName: String): Option[User] =  Users get userName
}

case class User(customuerNumber: String, firstName: String, lastName: String)
