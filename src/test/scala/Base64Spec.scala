import com.github.marklister.base64.Base64._
import utest._
import utest.ExecutionContext.RunNow

object MyTestSuite extends TestSuite {

  val tests = TestSuite {

    'test1 {
      assert("ABCDEFG".getBytes.toBase64 == ("QUJDREVGRw=="))
    }


    'test2 {
      assert("homuho".getBytes.toBase64 == ("aG9tdWhv"))
    }

    'test3 {
      assert("hogepiyofoobar".getBytes.toBase64 == ("aG9nZXBpeW9mb29iYXI="))
    }


    // Decoder tests

    'test4 {
      assert("aG9nZXBpeW9mb29iYXI=".toByteArray sameElements("hogepiyofoobar".getBytes))
    }

    'test5 {
      assert("+/+/+/+/".toByteArray.sameElements(("-_-_-_-_").toByteArray(base64Url)))
    }


    //RFC 4648    Test vectors

    'test6 {
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
  }
}



