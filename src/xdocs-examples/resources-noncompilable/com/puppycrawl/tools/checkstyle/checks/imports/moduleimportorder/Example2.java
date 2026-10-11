/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="ModuleImportOrder">
      <property name="separated" value="true"/>
    </module>
  </module>
</module>
*/
// non-compiled with javac: Compilable with Java25

package com.puppycrawl.tools.checkstyle.checks.imports.moduleimportorder;

// xdoc section - start
import module java.desktop;
import module java.sql;
import java.util.List; // violation ''java.util.List' should be separated'
// xdoc section - end

public class Example2 { }
