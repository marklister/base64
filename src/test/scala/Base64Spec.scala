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

  }
