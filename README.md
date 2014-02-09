#Base64

### Yet another Base64 encoder /decoder!

This one is public domain, idiomatic scala, and need not add a dependancy to 
your project.  Just copy the one source file!  It's less than 25 lines.

There are implict classes:

Encoder: `Array[Byte]=> String`

Decoder: `String => Array[Byte]`

Encoder's implict "trigger" is `toBase64`

Decoder's implicit "trigger" is `toByteArray`

You need to import 

`io.github.marklister.base64.Base64.Encoder`

`io.github.marklister.base64.Base64.Decoder`

or `io.github.marklister.base64.Base64._`


```scala
[info] Starting scala interpreter...
[info] 
import io.github.marklister.base64.Base64._
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
