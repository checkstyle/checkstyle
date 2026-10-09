/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="CustomImportOrder">
      <property name="customImportOrderRules"
        value="THIRD_PARTY_PACKAGE, SPECIAL_IMPORTS, STANDARD_JAVA_PACKAGE, STATIC"/>
      <property name="specialImportsRegExp" value="^javax\."/>
      <property name="standardPackageRegExp" value="^java\."/>
      <property name="sortImportsInGroupAlphabetically" value="true"/>
      <property name="separateLineBetweenGroups" value="false"/>
    </module>
  </module>
</module>
*/

// xdoc section - start
package com.puppycrawl.tools.checkstyle.checks.imports.customimportorder;

import static java.io.File.separator;
import static java.util.Collections.*;

// violation 2 lines below """Should be in the 'STANDARD_JAVA_PACKAGE' group,
//   expecting not assigned imports on this line."""
import java.time.*;

// violation 2 lines below """Should be in the 'SPECIAL_IMPORTS' group, expecting not
//   assigned imports on this line."""
import javax.net.*;

// violation 4 lines below """Import statement for 'org.apache.commons.io.FileUtils'
//   is in the wrong order. Should be in the
//   'THIRD_PARTY_PACKAGE' group, expecting not assigned
//   imports on this line."""
import org.apache.commons.io.FileUtils;

import com.puppycrawl.tools.checkstyle.checks.imports.CustomImportOrderCheck; // violation 'wrong order'
import com.puppycrawl.tools.checkstyle.checks.imports.ImportOrderCheck; // violation 'wrong order'
// xdoc section - end
public class Example10 {
}
