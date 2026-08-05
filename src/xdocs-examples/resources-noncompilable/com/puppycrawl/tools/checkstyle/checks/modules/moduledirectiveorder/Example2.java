/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="ModuleDirectiveOrder">
      <property name="order" value="requires, uses, provides, exports, opens"/>
    </module>
  </module>
</module>
*/
// non-compiled with javac: module declaration not in module-info.java

// xdoc section - start
module com.example.app {
  requires java.base;

  exports com.example.api;

  uses com.example.api.Service;
  // violation above ''uses' directive should be before 'exports' directive.'
}
// xdoc section - end
