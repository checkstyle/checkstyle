/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="ModuleImportOrder">
      <property name="option" value=" bottom "/>
    </module>
  </module>
</module>
*/

// non-compiled with javac: Compilable with Java25

package com.puppycrawl.tools.checkstyle.checks.imports.moduleimportorder;

// violation below 'Module import 'java.desktop' violates the configured relative order'
import module java.desktop;
import java.util.List;

public class InputModuleImportOrderOptionWhitespace {
    List<String> list;
}
