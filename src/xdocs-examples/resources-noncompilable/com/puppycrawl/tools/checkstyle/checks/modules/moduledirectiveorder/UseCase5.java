/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="ModuleDirectiveOrder"/>
  </module>
</module>
*/
// non-compiled with javac: module declaration not in module-info.java

// xdoc section - start
module com.example.app {
  requires java.base;

  requires java.sql;
  // violation above 'Empty line not allowed inside 'requires' directive block.'

  exports com.example.api;
}
// xdoc section - end
