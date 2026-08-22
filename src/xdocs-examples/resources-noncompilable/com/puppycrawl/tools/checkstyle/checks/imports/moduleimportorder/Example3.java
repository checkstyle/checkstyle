/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="ModuleImportOrder">
      <property name="option" value="bottom"/>
    </module>
  </module>
</module>
*/
// non-compiled with javac: Compilable with Java25

package com.puppycrawl.tools.checkstyle.checks.imports.moduleimportorder;

// xdoc section - start
// violation below 'Module import 'java.desktop' violates the configured'
import module java.desktop;
import java.util.List;

import module java.sql;
// xdoc section - end

public class Example3 { }
