object Benchmark {
  import com.github.marklister.base64.Base64._

  def main (args:Array[String] ): Unit ={
    println
    println("Microbenchmark\n" +
            "===============")

    val test=
      """
        |Josefsson                   Standards Track                     [Page 1]
        |
        |RFC 4648                    Base-N Encodings                October 2006
        |
        |
        |Table of Contents
        |
        |   1. Introduction ....................................................3
        |   2. Conventions Used in This Document ...............................3
        |   3. Implementation Discrepancies ....................................3
        |      3.1. Line Feeds in Encoded Data .................................3
        |      3.2. Padding of Encoded Data ....................................4
        |      3.3. Interpretation of Non-Alphabet Characters in Encoded Data ..4
        |      3.4. Choosing the Alphabet ......................................4
        |      3.5. Canonical Encoding .........................................5
        |   4. Base 64 Encoding ................................................5
        |   5. Base 64 Encoding with URL and Filename Safe Alphabet ............7
        |   6. Base 32 Encoding ................................................8
        |   7. Base 32 Encoding with Extended Hex Alphabet ....................10
        |   8. Base 16 Encoding ...............................................10
        |   9. Illustrations and Examples .....................................11
        |   10. Test Vectors ..................................................12
        |   11. ISO C99 Implementation of Base64 ..............................14
        |   12. Security Considerations .......................................14
        |   13. Changes Since RFC 3548 ........................................15
        |   14. Acknowledgements ..............................................15
        |   15. Copying Conditions ............................................15
        |   16. References ....................................................16
        |      16.1. Normative References .....................................16
        |      16.2. Informative References ...................................16
        |
        |
        |
        |
        |
        |
        |
        |
        |
        |
        |
        |
        |
        |
        |
        |
        |
        |
        |
        |
        |
        |
        |
        |
        |
        |
        |Josefsson                   Standards Track                     [Page 2]
        |
        |RFC 4648                    Base-N Encodings                October 2006
        |
        |
        |1.  Introduction
        |
        |   Base encoding of data is used in many situations to store or transfer
        |   data in environments that, perhaps for legacy reasons, are restricted
        |   to US-ASCII [1] data.  Base encoding can also be used in new
        |   applications that do not have legacy restrictions, simply because it
        |   makes it possible to manipulate objects with text editors.
        |
        |   In the past, different applications have had different requirements
        |   and thus sometimes implemented base encodings in slightly different
        |   ways.  Today, protocol specifications sometimes use base encodings in
        |   general, and "base64" in particular, without a precise description or
        |   reference.  Multipurpose Internet Mail Extensions (MIME) [4] is often
        |   used as a reference for base64 without considering the consequences
        |   for line-wrapping or non-alphabet characters.  The purpose of this
        |   specification is to establish common alphabet and encoding
        |   considerations.  This will hopefully reduce ambiguity in other
        |   documents, leading to better interoperability.
        |
        |2.  Conventions Used in This Document
        |
        |   The key words "MUST", "MUST NOT", "REQUIRED", "SHALL", "SHALL NOT",
        |   "SHOULD", "SHOULD NOT", "RECOMMENDED", "MAY", and "OPTIONAL" in this
        |   document are to be interpreted as described in [2].
        |
        |3.  Implementation Discrepancies
        |
        |   Here we discuss the discrepancies between base encoding
        |   implementations in the past and, where appropriate, mandate a
        |   specific recommended behavior for the future.
        |
        |3.1.  Line Feeds in Encoded Data
        |
        |   MIME [4] is often used as a reference for base 64 encoding.  However,
        |   MIME does not define "base 64" per se, but rather a "base 64 Content-
        |   Transfer-Encoding" for use within MIME.  As such, MIME enforces a
        |   limit on line length of base 64-encoded data to 76 characters.  MIME
        |   inherits the encoding from Privacy Enhanced Mail (PEM) [3], stating
        |   that it is "virtually identical"; however, PEM uses a line length of
        |   64 characters.  The MIME and PEM limits are both due to limits within
        |   SMTP.
        |
        |   Implementations MUST NOT add line feeds to base-encoded data unless
        |   the specification referring to this document explicitly directs base
        |   encoders to add line feeds after a specific number of characters.
        |
        |
        |
        |
        |
        |
        |Josefsson                   Standards Track                     [Page 3]
        |
        |RFC 4648                    Base-N Encodings                October 2006
        |
        |
        |3.2.  Padding of Encoded Data
        |
        |   In some circumstances, the use of padding ("=") in base-encoded data
        |   is not required or used.  In the general case, when assumptions about
        |   the size of transported data cannot be made, padding is required to
        |   yield correct decoded data.
        |
        |   Implementations MUST include appropriate pad characters at the end of
        |   encoded data unless the specification referring to this document
        |   explicitly states otherwise.
        |
        |   The base64 and base32 alphabets use padding, as described below in
        |   sections 4 and 6, but the base16 alphabet does not need it; see
        |   section 8.
        |
        |3.3.  Interpretation of Non-Alphabet Characters in Encoded Data
        |
        |   Base encodings use a specific, reduced alphabet to encode binary
        |   data.  Non-alphabet characters could exist within base-encoded data,
        |   caused by data corruption or by design.  Non-alphabet characters may
        |   be exploited as a "covert channel", where non-protocol data can be
        |   sent for nefarious purposes.  Non-alphabet characters might also be
        |   sent in order to exploit implementation errors leading to, e.g.,
        |   buffer overflow attacks.
        |
        |   Implementations MUST reject the encoded data if it contains
        |   characters outside the base alphabet when interpreting base-encoded
        |   data, unless the specification referring to this document explicitly
        |   states otherwise.  Such specifications may instead state, as MIME
        |   does, that characters outside the base encoding alphabet should
        |   simply be ignored when interpreting data ("be liberal in what you
        |   accept").  Note that this means that any adjacent carriage return/
        |   line feed (CRLF) characters constitute "non-alphabet characters" and
        |   are ignored.  Furthermore, such specifications MAY ignore the pad
        |   character, "=", treating it as non-alphabet data, if it is present
        |   before the end of the encoded data.  If more than the allowed number
        |   of pad characters is found at the end of the string (e.g., a base 64
        |   string terminated with "==="), the excess pad characters MAY also be
        |   ignored.
        |
        |3.4.  Choosing the Alphabet
        |
        |   Different applications have different requirements on the characters
        |   in the alphabet.  Here are a few requirements that determine which
        |   alphabet should be used:
        |
        |
        |
        |
        |
        |
        |Josefsson                   Standards Track                     [Page 4]
        |
        |RFC 4648                    Base-N Encodings                October 2006
        |
        |
        |   o  Handled by humans.  The characters "0" and "O" are easily
        |      confused, as are "1", "l", and "I".  In the base32 alphabet below,
        |      where 0 (zero) and 1 (one) are not present, a decoder may
        |      interpret 0 as O, and 1 as I or L depending on case.  (However, by
        |      default it should not; see previous section.)
        |
        |   o  Encoded into structures that mandate other requirements.  For base
        |      16 and base 32, this determines the use of upper- or lowercase
        |      alphabets.  For base 64, the non-alphanumeric characters (in
        |      particular, "/") may be problematic in file names and URLs.
        |
        |   o  Used as identifiers.  Certain characters, notably "+" and "/" in
        |      the base 64 alphabet, are treated as word-breaks by legacy text
        |      search/index tools.
        |
        |   There is no universally accepted alphabet that fulfills all the
        |   requirements.  For an example of a highly specialized variant, see
        |   IMAP [8].  In this document, we document and name some currently used
        |   alphabets.
        |
        |3.5.  Canonical Encoding
        |
        |   The padding step in base 64 and base 32 encoding can, if improperly
        |   implemented, lead to non-significant alterations of the encoded data.
        |   For example, if the input is only one octet for a base 64 encoding,
        |   then all six bits of the first symbol are used, but only the first
        |   two bits of the next symbol are used.  These pad bits MUST be set to
        |   zero by conforming encoders, which is described in the descriptions
        |   on padding below.  If this property do not hold, there is no
        |   canonical representation of base-encoded data, and multiple base-
        |   encoded strings can be decoded to the same binary data.  If this
        |   property (and others discussed in this document) holds, a canonical
        |   encoding is guaranteed.
        |
        |   In some environments, the alteration is critical and therefore
        |   decoders MAY chose to reject an encoding if the pad bits have not
        |   been set to zero.  The specification referring to this may mandate a
        |   specific behaviour.
        |
        |4.  Base 64 Encoding
        |
        |   The following description of base 64 is derived from [3], [4], [5],
        |   and [6].  This encoding may be referred to as "base64".
        |
        |   The Base 64 encoding is designed to represent arbitrary sequences of
        |   octets in a form that allows the use of both upper- and lowercase
        |   letters but that need not be human readable.
        |
        |
        |
        |
        |Josefsson                   Standards Track                     [Page 5]
        |
        |RFC 4648                    Base-N Encodings                October 2006
        |
        |
        |   A 65-character subset of US-ASCII is used, enabling 6 bits to be
        |   represented per printable character.  (The extra 65th character, "=",
        |   is used to signify a special processing function.)
        |
        |   The encoding process represents 24-bit groups of input bits as output
        |   strings of 4 encoded characters.  Proceeding from left to right, a
        |   24-bit input group is formed by concatenating 3 8-bit input groups.
        |   These 24 bits are then treated as 4 concatenated 6-bit groups, each
        |   of which is translated into a single character in the base 64
        |   alphabet.
        |
        |   Each 6-bit group is used as an index into an array of 64 printable
        |   characters.  The character referenced by the index is placed in the
        |   output string.
        |
        |                      Table 1: The Base 64 Alphabet
        |
        |     Value Encoding  Value Encoding  Value Encoding  Value Encoding
        |         0 A            17 R            34 i            51 z
        |         1 B            18 S            35 j            52 0
        |         2 C            19 T            36 k            53 1
        |         3 D            20 U            37 l            54 2
        |         4 E            21 V            38 m            55 3
        |         5 F            22 W            39 n            56 4
        |         6 G            23 X            40 o            57 5
        |         7 H            24 Y            41 p            58 6
        |         8 I            25 Z            42 q            59 7
        |         9 J            26 a            43 r            60 8
        |        10 K            27 b            44 s            61 9
        |        11 L            28 c            45 t            62 +
        |        12 M            29 d            46 u            63 /
        |        13 N            30 e            47 v
        |        14 O            31 f            48 w         (pad) =
        |        15 P            32 g            49 x
        |        16 Q            33 h            50 y
        |
        |   Special processing is performed if fewer than 24 bits are available
        |   at the end of the data being encoded.  A full encoding quantum is
        |   always completed at the end of a quantity.  When fewer than 24 input
        |   bits are available in an input group, bits with value zero are added
        |   (on the right) to form an integral number of 6-bit groups.  Padding
        |   at the end of the data is performed using the '=' character.  Since
        |   all base 64 input is an integral number of octets, only the following
        |   cases can arise:
        |
        |   (1) The final quantum of encoding input is an integral multiple of 24
        |       bits; here, the final unit of encoded output will be an integral
        |       multiple of 4 characters with no "=" padding.
        |
        |
      """.stripMargin.getBytes()
    val res1=test.toBase64
    val res2=test.toBase64
    System.gc()
    val start=new java.util.Date().getTime
    for(x<- 1 to 100) {test.toBase64
      print(".")}
    val end=new java.util.Date().getTime
    println ("Encode microbenchmank:" )
    println ("Encode size:"+ test.size*100+" bytes")
    println ("Encode time:" +(end-start)+" ms")
    println()

    val test2=test.toBase64
    test2.toByteArray
    System.gc()
    val start2=new java.util.Date().getTime
    for(x<- 1 to 100) {test2.toByteArray
      print(".")}
    val end2=new java.util.Date().getTime
    println("Decode microbenchmark")
    println ("Decode size:"+ test2.size*100+" chars")
    println ("Decode time:" +(end2-start2)+" ms")
    println()
    ///////////////////////////

    //Uncomment this code to compare with Apache.  Apache is about 40 times faster.
  /*  import org.apache.commons.codec.binary.Base64._

    val res11= encodeBase64(test)
    val res12= encodeBase64(test)

    System.gc()
    val start3=new java.util.Date().getTime
    for(x<- 1 to 100) {encodeBase64(test)
      print(".")}
    val end3=new java.util.Date().getTime
    println ("Apache Encode microbenchmank:" )
    println ("Encode size:"+ test.size*100+" bytes")
    println ("Encode time:" +(end3-start3)+" ms")
    println()

    val test21=encodeBase64(test)
    decodeBase64(test21)

    System.gc()
    val start4=new java.util.Date().getTime
    for(x<- 1 to 100) {decodeBase64(test21)
    print(".")}
    val end4=new java.util.Date().getTime
    println("Apache Decode microbenchmark")
    println ("Decode size:"+ test21.size*100+" chars")
    println ("Decode time:" +(end4-start4)+" ms")
    println()

*/
  }
}
