/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="ModuleImportOrder"/>
  </module>
</module>
*/
// non-compiled with javac: Compilable with Java25

package com.puppycrawl.tools.checkstyle.checks.imports.moduleimportorder;

// xdoc section - start
import module java.sql;
// violation below 'Wrong lexicographical order for module import 'java.desktop''
import module java.desktop;
import java.util.List;
// violation below 'Module import 'java.logging' violates the configured'
import module java.logging;
// xdoc section - end

public class Example1 { }
