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

  exports com.example.api;

  requires java.sql;
  // violation above 'All 'requires' directives should be in a single block.'
}
// xdoc section - end
