#Base64

### Yet another Base64 encoder!

This one is public domain, idiomatic scala, and need not add a dependancy to 
your project.  Just copy the one source file!  It's less than 25 lines.

There is an implict class that converts an `Array[Byte]` to a `String` if you invoke `toBase64` method.
You need to make sure io.github.marklister.base64.Base64 is imported in current scope

```scala
import io.github.marklister.base64.Base64._
Welcome to Scala version 2.10.2 (OpenJDK Server VM, Java 1.7.0_51).
Type in expressions to have them evaluated.
Type :help for more information.

scala> "ABCDEFG".getBytes.toBase64
res0: String = QUJDREVGRw==
```