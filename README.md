#Base64

### Another Base64 encoder / decoder.

This one is 
 + really small
 + not very efficient
 + liberal licence (BSD)*,
 + has a natural API
 + idiomatic scala, and 
 + need not add a dependency to your project.  Just copy the [one source file](https://github.com/marklister/base64/blob/master/src/main/scala/Base64.scala) to your project.  It's around 36 lines of code.

#### Getting Started

Using SBT:
```scala
     libraryDependencies += "com.github.marklister" %% "base64" % "0.1.1"
```
or for scala-js
```scala
     libraryDependencies += "com.github.marklister" %%% "base64" % "0.1.1"
```

#### Natural API

You simply invoke `toBase64` on an `Array[Byte]` or

`toByteArray` on a `String` containing a Base64 representation.

####Imports

`com.github.marklister.base64.Base64.Encoder`

`com.github.marklister.base64.Base64.Decoder`

or the wildcard:

`com.github.marklister.base64.Base64._`

####Efficiency

This implementation chooses simplicity over efficiency. If you are doing heavy duty encoding or decoding you might
want to look around for something more industrial.

####REPL example

```

    scala
    [info] Starting scala interpreter...
    [info] 
    import com.github.marklister.base64.Base64._
    Welcome to Scala version 2.10.2 (OpenJDK Server VM, Java 1.7.0_51).
    Type in expressions to have them evaluated.
    Type :help for more information.
    
    scala> "ABCDEFG".getBytes.toBase64 //Encodes Array[Byte]=>String
    res0: String = QUJDREVGRw==
    
    scala> res0.toByteArray //Decodes base64 String=>Array[Byte]
    res1: Array[Byte] = Array(65, 66, 67, 68, 69, 70, 71)
    
    scala> res1.sameElements("ABCDEFG".getBytes)
    res2: Boolean = true

```

####Encoding: Base64 vs Base64url

You can switch encoding by exposing the encoding as an implicit parameter:

```
    
    scala> "+/+/+/+/".toByteArray //standard Base64
    res5: Array[Byte] = Array(-5, -1, -65, -5, -1, -65)
    
    scala> implicit val encoding = io.github.marklister.base64.Base64.base64Url
    encoding: io.github.marklister.base64.Base64.B64Scheme = io.github.marklister.base64.Base64$B64Scheme@9c5e61
    
    scala> res5.toBase64
    res6: String = -_-_-_-_ //Base64-url
```

And with the Base64 object already imported this simplifies to:

```
    implicit val encoding = base64Url
```

It's trivial to create your own encoding.

####Mix encodings

If you need to mix encodings then have a look at the tests for an example.

* At one stage this code was released as public domain but there was some discussion about the validity of public
  domain licences and they don't work on Maven Central.  So now it's BSD.  If you need some other licence then raise an
  issue.
