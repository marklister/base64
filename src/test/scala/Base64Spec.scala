import io.github.marklister.base64.Base64._
import org.specs2.mutable.Specification

class Base64Spec extends Specification {

  "ABCDEFG" should {
    "encode as QUJDREVGRw==" in {
      "ABCDEFG".getBytes.toBase64 must beEqualTo("QUJDREVGRw==")
    }
  }

  "homuho" should {
    "encode as aG9tdWhv" in {
      "homuho".getBytes.toBase64 must beEqualTo("aG9tdWhv")
    }
  }
  "hogepiyofoobar" should {
    "encode as aG9nZXBpeW9mb29iYXI=" in {
      "hogepiyofoobar".getBytes.toBase64 must beEqualTo("aG9nZXBpeW9mb29iYXI=")
    }
  }

  // Decoder tests

  "aG9nZXBpeW9mb29iYXI=" should {
    "decode as hogepiyofoobar" in {
      "aG9nZXBpeW9mb29iYXI=".toByteArray must beEqualTo("hogepiyofoobar".getBytes)
    }
  }
  "+/+/+/+/ base64" should {
    " be equivalent to -_-_-_-_ base64Url" in {
      "+/+/+/+/".toByteArray.sameElements(("-_-_-_-_").toByteArray(base64Url)) must beTrue
    }
  }

  //RFC 4648    Test vectors

  "0x14fb9c03d97e" should {
    "encode as F      P      u      c        A      9      l      +" in {
      BigInt("14fb9c03d97e", 16).toByteArray.toBase64 must beEqualTo("FPucA9l+")
    }
  }

  val testVectors = Seq("" -> "",
    "f" -> "Zg==",
    "fo" -> "Zm8=",
    "foo" -> "Zm9v",
    "foob" -> "Zm9vYg==",
    "fooba" -> "Zm9vYmE=",
    "foobar" ->"Zm9vYmFy"
  )

  for (t<-testVectors) { //test encoding
    t._1 should {
      "encode as " + t._2 in {
        t._1.getBytes.toBase64 must beEqualTo(t._2)
      }
    }
  }

  for (t<-testVectors) { //test decoding
    t._2 should {
      "decode as " + t._1 in {
        t._2.toByteArray sameElements(t._1.getBytes) must beTrue
      }
    }
  }



}
