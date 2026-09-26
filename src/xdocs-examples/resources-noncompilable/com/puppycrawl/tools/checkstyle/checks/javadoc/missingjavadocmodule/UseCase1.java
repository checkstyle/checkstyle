/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="MissingJavadocModule"/>
  </module>
</module>
*/
// non-compiled with javac: Compilable with Java9 individually
// xdoc section - start
/** Documents the annotated module. */
@Deprecated
open module com.example {
}
// xdoc section - end
