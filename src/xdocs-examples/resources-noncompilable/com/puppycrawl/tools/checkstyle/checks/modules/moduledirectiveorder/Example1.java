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
  requires transitive java.sql;

  exports com.example.api;

  opens com.example.model;

  uses com.example.api.Service;

  provides com.example.api.Service with com.example.impl.ServiceImpl;
}
// xdoc section - end
