/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="MissingJavadocModule"/>
  </module>
</module>
*/

// non-compiled with javac: Compilable with Java9 individually

module com.example { // violation 'Missing javadoc for module declaration.'
    /** Documents only the directive. */
    requires java.base;
}
