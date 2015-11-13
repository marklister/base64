#Base64

### Another Base64 encoder / decoder.

This one is 
 + really small
 + not very efficient
 + liberal licence (BSD)*,
 + simple API
 + idiomatic scala, and 
 + need not add a dependency to your project.  Just copy the [one source file](https://github.com/marklister/base64/blob/master/shared/src/main/scala/Base64.scala) to your project.
 + Scala-js compatible
 + No Dom required
 
#### Getting Started

Using SBT:
```scala
     libraryDependencies += "com.github.marklister" %% "base64" % "0.2.0"
```
or for scala-js
```scala
     libraryDependencies += "com.github.marklister" %%% "base64" % "0.2.0"
```

#### API

Simply invoke `toBase64` on an `Array[Byte]` or

`toByteArray` on a `String` containing a Base64 representation.

####Imports

`com.github.marklister.base64.Base64.Encoder`

`com.github.marklister.base64.Base64.Decoder`

or the wildcard:

`com.github.marklister.base64.Base64._`

####Base64 Url

Two encoding schemes are provided as default: `base64` and `base64Url`.  `base64` is the default one.  To select
`base64Url`

Using implicits -- just make the encode/decode scheme available via an implicit:
 
 ```
 scala
 implicit val encoding = base64Url
 ```
 
 Or you can provide the scheme as an argument to `toByteArray` or `toBase64` eg `toBase64(base64Url)` or `toByteArray(base64)`
  
####Padding

Padding is strict for the `base64` encode scheme and non-strict for `base64Url`

To create a non strict scheme:

```
scala
implicit val encoding = base64.copy(strictPadding=false)
```

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


* At one stage this code was released as public domain but there was some discussion about the validity of public
  domain licences and they don't work on Maven Central.  So now it's BSD.  If you need some other licence then raise an
  issue.
