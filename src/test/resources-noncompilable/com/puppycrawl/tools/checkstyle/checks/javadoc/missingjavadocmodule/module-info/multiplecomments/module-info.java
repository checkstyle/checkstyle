/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="MissingJavadocModule"/>
  </module>
</module>
*/

// non-compiled with javac: Compilable with Java9 individually

/** First description. */
/** Last description. */
// An intervening line comment.
module com.example {
}
