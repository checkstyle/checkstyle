/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="ModuleDirectiveOrder">
      <property name="validateBlockSeparation" value="false"/>
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
}
// xdoc section - end
