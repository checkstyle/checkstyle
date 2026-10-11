/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="AvoidModuleImport">
      <property name="excludes" value="java.base, java.sql"/>
    </module>
  </module>
</module>
*/

// non-compiled with javac: Compilable with Java25

package com.puppycrawl.tools.checkstyle.checks.imports.avoidmoduleimport;

// xdoc section - start
import module java.base;

import module java.sql;

public class Example2 {

  void getNames() {
    List<String> names = new ArrayList<>();
    names.add("foo");
    names.add("bar");
    System.out.println(names);
  }
}
// xdoc section - end
