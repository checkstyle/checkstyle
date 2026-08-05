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
  // violation above 'separated from the previous block by exactly one empty line'
}
// xdoc section - end
