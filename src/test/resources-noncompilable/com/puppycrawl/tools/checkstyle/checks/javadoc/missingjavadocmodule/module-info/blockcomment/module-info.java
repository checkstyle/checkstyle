/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="MissingJavadocModule"/>
  </module>
</module>
*/

// non-compiled with javac: Compilable with Java9 individually

/* A plain block comment. */
module example { // violation 'Missing javadoc for module declaration.'
}
