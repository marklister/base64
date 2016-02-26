import com.github.marklister.base64.Base64._
import utest._
import utest.ExecutionContext.RunNow

object MyTestSuite extends TestSuite {

  val tests = TestSuite {

    'encode1 {
      assert("ABCDEFG".getBytes.toBase64 == ("QUJDREVGRw=="))
    }


    'encode2 {
      assert("homuho".getBytes.toBase64 == ("aG9tdWhv"))
    }

    'encode3 {
      assert("hogepiyofoobar".getBytes.toBase64 == ("aG9nZXBpeW9mb29iYXI="))
    }


    // Decoder tests

    'decode4 {
      assert("aG9nZXBpeW9mb29iYXI=".toByteArray sameElements ("hogepiyofoobar".getBytes))
    }


    'decode5 {
      assert("+/+/+/+/".toByteArray.sameElements(("-_-_-_-_").toByteArray(base64Url)))
    }


    //RFC 4648    Test vectors

    'testBigInt {
      assert(BigInt("14fb9c03d97e", 16).toByteArray.toBase64 == ("FPucA9l+"))
    }


    'test7 {
      val testVectors = Seq("" -> "",
        "f" -> "Zg==",
        "fo" -> "Zm8=",
        "foo" -> "Zm9v",
        "foob" -> "Zm9vYg==",
        "fooba" -> "Zm9vYmE=",
        "foobar" -> "Zm9vYmFy"
      )

      for (t <- testVectors) {
        //test encoding
        assert(t._1.getBytes.toBase64 == (t._2))
      }



      for (t <- testVectors) {
        //test decoding
        assert(t._2.toByteArray sameElements (t._1.getBytes))
      }
    }

    'testunpaddedDecode {
      val testVectors = Seq("" -> "",
        "f" -> "Zg==",
        "fo" -> "Zm8=",
        "foo" -> "Zm9v",
        "foob" -> "Zm9vYg==",
        "fooba" -> "Zm9vYmE=",
        "foobar" -> "Zm9vYmFy"
      )

      for (t <- testVectors) {
        //test decoding
        assert(t._2.reverse.dropWhile(_ == '=').reverse.toByteArray(base64Url) sameElements (t._1.getBytes))
      }
    }

    'testBase64UrlPadding {
      val testVectors = Seq("" -> "",
        "f" -> "Zg%3D%3D",
        "fo" -> "Zm8%3D",
        "foo" -> "Zm9v",
        "foob" -> "Zm9vYg%3D%3D",
        "fooba" -> "Zm9vYmE%3D",
        "foobar" -> "Zm9vYmFy"
      )

      for (t <- testVectors) {
        //test encoding
        assert(t._1.getBytes.toBase64(base64Url) == t._2)
      }
    }

    'testBase64UrlDecoding {
      val testVectors = Seq("" -> "",
        "f" -> "Zg%3D%3D",
        "fo" -> "Zm8%3D",
        "foo" -> "Zm9v",
        "foob" -> "Zm9vYg%3D%3D",
        "fooba" -> "Zm9vYmE%3D",
        "foobar" -> "Zm9vYmFy"
      )

      for (t <- testVectors) {
        //test encoding
        assert(t._2.toByteArray(base64Url) sameElements (t._1.getBytes))
      }
    }
  }
}



