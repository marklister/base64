package com.github.marklister.base64

/**
 * Base64 encoder
 * @author Mark Lister
 * This software is distributed under the 2-Clause BSD license. See the
 * LICENSE file in the root of the repository.
 *
 * Copyright (c) 2014 Mark Lister
 *
 *         The repo for this Base64 encoder lives at  https://github.com/marklister/base64
 *         Please send your issues, suggestions and pull requests there.
 *
 */

object Base64 {

  class B64Scheme(val encodeTable: IndexedSeq[Char]) {
    lazy val decodeTable = {
      val d = new Array[Int](128)
      encodeTable.zipWithIndex.foreach(x => d(x._1.toInt) = x._2)
      d
    }
  }

  lazy val base64 = new B64Scheme(('A' to 'Z') ++ ('a' to 'z') ++ ('0' to '9') ++ Seq('+', '/'))
  lazy val base64Url = new B64Scheme(base64.encodeTable.dropRight(2) ++ Seq('-', '_'))

  implicit class Encoder(b: Array[Byte]) {
    private[this] val zero = Array(0, 0).map(_.toByte)
    lazy val pad = (3 - b.length % 3) % 3

    def toBase64(implicit scheme: B64Scheme = base64): String = {
      def sixBits(x: Array[Byte]): Seq[Int] = {
        val a = (x(0) & 0xfc) >> 2
        val b = ((x(0) & 0x3) << 4) | ((x(1) & 0xf0) >> 4)
        val c = ((x(1) & 0xf) << 2) | ((x(2) & 0xc0) >> 6)
        val d = (x(2)) & 0x3f
        Seq(a, b, c, d)
      }
      ((b ++ zero.take(pad)).grouped(3)
        .flatMap(sixBits(_))
        .map(x => scheme.encodeTable(x))
        .toSeq
        .dropRight(pad) :+ "=" * pad)
        .mkString
    }
  }

  implicit class Decoder(s: String) {
    lazy val cleanS = s.reverse.dropWhile(_ == '=').reverse
    lazy val pad = s.length - cleanS.length

    def toByteArray(implicit scheme: B64Scheme = base64): Array[Byte] = {
      def threeBytes(s: Seq[Char]): Array[Byte] = {
        val r = s.map(scheme.decodeTable(_)).foldLeft(0)((a, b) => (a << 6) | b)
        Array((r >> 16).toByte, (r >> 8).toByte, r.toByte)
      }
      if (pad > 2 || s.length % 4 != 0) throw new java.lang.IllegalArgumentException("Invalid Base64 String:" + s)
      if (!cleanS.forall(scheme.encodeTable.contains(_))) throw new java.lang.IllegalArgumentException("Invalid Base64 String:" + s)

      (cleanS + "A" * pad)
        .grouped(4)
        .map(threeBytes(_))
        .flatten
        .toArray
        .dropRight(pad)
    }
  }

}

