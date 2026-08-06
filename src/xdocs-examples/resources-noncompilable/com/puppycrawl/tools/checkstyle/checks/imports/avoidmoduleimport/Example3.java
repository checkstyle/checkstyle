/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="AvoidModuleImport">
      <property name="maxAllowedModuleImports" value="1"/>
    </module>
  </module>
</module>
*/

// non-compiled with javac: Compilable with Java25

package com.puppycrawl.tools.checkstyle.checks.imports.avoidmoduleimport;

// xdoc section - start
import module java.base;

import module java.sql;
// violation above 'Only '1' module import is allowed per file.'
public class Example3 {

  void getNames() {
    List<String> names = new ArrayList<>();
    names.add("foo");
    names.add("bar");
    System.out.println(names);
  }
}
// xdoc section - end
