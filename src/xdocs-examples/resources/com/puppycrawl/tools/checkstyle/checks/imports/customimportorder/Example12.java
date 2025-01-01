/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="CustomImportOrder">
      <property name="customImportOrderRules"
        value="STATIC, SAME_PACKAGE(3), THIRD_PARTY_PACKAGE, STANDARD_JAVA_PACKAGE"/>
      <property name="thirdPartyPackageRegExp" value="^(com|org)\."/>
      <property name="standardPackageRegExp" value="^(java|javax)\."/>
    </module>
  </module>
</module>
*/

// xdoc section -- start
package com.puppycrawl.tools.checkstyle.checks.imports.customimportorder;

import static java.io.File.separator;
import static java.util.Collections.*;
import java.time.*; // violation, "wrong order"
import javax.net.*; // violation, "wrong order"

import com.puppycrawl.tools.checkstyle.checks.imports.CustomImportOrderCheck;
import com.puppycrawl.tools.checkstyle.checks.imports.ImportOrderCheck;

import org.apache.commons.io.FileUtils;
// xdoc section -- end
public class Example12 {
}
