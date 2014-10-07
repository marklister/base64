package io.github.marklister.base64
import scala.annotation.tailrec

/**
 * Base64 encoder
 * @author Mark Lister
 * (c) Mark Lister 2014
 *
 * I, the copyright holder of this work, release this work into the public domain.
 * This applies worldwide. In some countries this may not be legally possible;
 * if so: I grant anyone the right to use this work for any purpose, without any
 * conditions, unless such conditions are required by law.
 * 
 * The repo for this Base64 encoder lives at  https://github.com/marklister/base64
 * Please send your issues, suggestions and pull requests there.
 *
 */

object Base64 {

  class  B64Scheme (val encodeTable:IndexedSeq[Char]){
    lazy val decodeTable=collection.immutable.TreeMap(encodeTable.zipWithIndex : _*)
  }

  lazy val base64= new B64Scheme (('A' to 'Z') ++ ('a' to 'z') ++ ('0' to '9') ++ Seq('+', '/'))
  lazy val base64Url= new B64Scheme ( base64.encodeTable.dropRight(2) ++ Seq('-', '_'))

  implicit class Encoder(b: Array[Byte])  {
    private[this] val basic = Array(127, 127, 127).map(_.toByte) //to force only positive BigInts
    private[this] val zero = Array(0, 0).map(_.toByte)

    def toBase64 (implicit  scheme:B64Scheme=base64): String = {
      @tailrec
      def enc(z: BigInt,acc:Seq[Char]): Seq[Char] =
        if (z == 0) acc
        else  enc(z / 64,scheme.encodeTable((z % 64).toInt)+:acc)

      val pad = (3 - b.length % 3) % 3
      val bi = BigInt(basic ++ b ++ zero.take(pad))

      (enc(bi,Seq.empty).drop(4).dropRight(pad) :+ "=" * pad).mkString
    }
  }

  implicit class Decoder(s: String) {
    lazy val cleanS = s.reverse.dropWhile(_ == '=').reverse
    lazy val pad = s.length - cleanS.length

    def toByteArray(implicit scheme:B64Scheme=base64): Array[Byte] = {
      if (pad > 2 || s.length % 4 != 0) throw new java.lang.IllegalArgumentException("Invalid Base64 String:" + s)
      if (!cleanS.forall(scheme.encodeTable.contains(_))) throw new java.lang.IllegalArgumentException("Invalid Base64 String:" + s)

      (cleanS + "A" * pad)
        .foldLeft(BigInt(127))((a, b) => a * 64 + scheme.decodeTable(b))
        .toByteArray
        .drop(1).dropRight(pad)
    }
  }
}

