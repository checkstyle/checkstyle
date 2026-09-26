/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="MissingJavadocModule"/>
  </module>
</module>
*/

// non-compiled with javac: Compilable with Java9 individually

open /** Misplaced Javadoc. */ module com.example { // violation 'Missing javadoc'
}
