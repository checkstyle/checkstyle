/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="CustomImportOrder">
      <property name="customImportOrderRules"
        value="STATIC, STANDARD_JAVA_PACKAGE, THIRD_PARTY_PACKAGE"/>
      <property name="standardPackageRegExp" value="^java\."/>
    </module>
  </module>
</module>
*/
// xdoc section -- start
package com.puppycrawl.tools.checkstyle.checks.imports.customimportorder;

import static java.util.Collections.*;
import static java.io.File.separator;

import java.time.*;
import javax.net.*; // violation, 'should be separated'

import org.apache.commons.io.FileUtils; // violation, 'Extra separation'
import com.puppycrawl.tools.checkstyle.checks.imports.CustomImportOrderCheck;
import com.puppycrawl.tools.checkstyle.checks.imports.ImportOrderCheck;
// xdoc section -- end
public class Example3 {
}
