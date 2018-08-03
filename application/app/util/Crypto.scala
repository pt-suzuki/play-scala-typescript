package util

import java.security.MessageDigest
import java.util.{Date, UUID}

import javax.xml.bind.DatatypeConverter
import sun.misc.BASE64Encoder

import scala.util.Random

object Crypto {
  def generateToken(): String = {
    val key = UUID.randomUUID.toString()
    new BASE64Encoder().encode(key.getBytes())
  }

  /**
   * Generate an Auth Code by creating a SHA-1 digest of current date,
   *  and random string of length 100 characters.
   * @return Hex encoded SHA-1 digest.
   */
  def generateAuthCode(): String = {
    val md = MessageDigest.getInstance("SHA-1")
    val date = new Date
    val randomString = Random.nextString(100)
    md.update(date.toString().getBytes())
    md.update(randomString.getBytes())
    DatatypeConverter.printHexBinary(md.digest)
  }

  def generateUUID(): String = {
    UUID.randomUUID().toString
  }

}