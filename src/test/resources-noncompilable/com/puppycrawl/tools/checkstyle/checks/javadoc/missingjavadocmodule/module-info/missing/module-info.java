/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="MissingJavadocModule">
      <property name="tokens" value="MODULE_DEF"/>
    </module>
  </module>
</module>
*/

// non-compiled with javac: Compilable with Java21 individually

module com.example { // violation 'Missing javadoc for module declaration.'
}
