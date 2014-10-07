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
  }
