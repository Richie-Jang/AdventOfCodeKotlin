package day4

import java.security.MessageDigest
import javax.xml.bind.DatatypeConverter

/**
 * Created by richi on 2016-01-01.
 */

fun getMD5HashBytes (value: String): ByteArray {
    val md5 = MessageDigest.getInstance("MD5")
    return md5.digest(value.toByteArray())
}

fun getHexString(ba : ByteArray): String {
    return DatatypeConverter.printHexBinary(ba)
}

fun main (args: Array<String>) {
    val str = "yzbqklnj"

    val result = (0..Int.MAX_VALUE)
        .find {
            k -> val key = getHexString(getMD5HashBytes(str+k.toString()))
            if (key.startsWith("000000")) true else false
        }
    println (result)
//    val str = "abcdef609043"
//    println (getHexString(getMD5HashBytes(str)))
}