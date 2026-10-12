/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="CustomImportOrder">
      <property name="sortImportsInGroupAlphabetically" value="true"/>
    </module>
  </module>
</module>
*/

// xdoc section - start
package com.puppycrawl.tools.checkstyle.checks.imports.customimportorder;

import java.awt.Dialog;
import java.awt.Window;
import java.awt.color.ColorSpace;
// violation 2 lines below """Wrong lexicographical order for 'java.awt.Frame'
//   import. Should be before 'java.awt.color.ColorSpace'."""
import java.awt.Frame;

// xdoc section - end
public class Example14 {
}
