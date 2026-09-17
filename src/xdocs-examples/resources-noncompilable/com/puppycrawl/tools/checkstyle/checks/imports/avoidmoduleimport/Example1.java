/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="AvoidModuleImport"/>
  </module>
</module>
*/

// non-compiled with javac: Compilable with Java25

package com.puppycrawl.tools.checkstyle.checks.imports.avoidmoduleimport;

// xdoc section - start
import module java.base;
// violation above 'Using the 'import module' form of import should be avoided'
import module java.sql;
// violation above 'Using the 'import module' form of import should be avoided'
public class Example1 {

  void getNames() {
    List<String> names = new ArrayList<>();
    names.add("foo");
    names.add("bar");
    System.out.println(names);
  }
}
// xdoc section - end
