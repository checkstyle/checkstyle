/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="CustomImportOrder">
      <property name="sortImportsInGroupAlphabetically" value="true"/>
    </module>
  </module>
</module>
*/

// xdoc section -- start
package com.puppycrawl.tools.checkstyle.checks.imports.customimportorder;

import java.awt.Dialog;
import java.awt.Window;
import java.awt.color.ColorSpace;
import java.awt.Frame; // violation, in ASCII order all uppercase comes before lowercase letters

// xdoc section -- end
public class Example14 {
}
