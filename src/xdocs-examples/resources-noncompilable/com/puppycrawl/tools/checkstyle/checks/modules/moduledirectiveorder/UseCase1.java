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
  exports com.example.api;

  requires java.base;
  // violation above ''requires' directive should be before 'exports' directive.'
}
// xdoc section - end
