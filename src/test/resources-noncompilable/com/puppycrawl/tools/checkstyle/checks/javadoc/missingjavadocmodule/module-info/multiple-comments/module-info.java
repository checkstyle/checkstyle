/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="MissingJavadocModule"/>
  </module>
</module>
*/

// non-compiled with javac: Compilable with Java21 individually

/** First description. */
/** Last description. */
// An intervening line comment.
module com.example {
}
