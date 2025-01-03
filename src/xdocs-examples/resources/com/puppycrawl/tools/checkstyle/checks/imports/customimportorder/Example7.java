/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="CustomImportOrder">
      <property name="customImportOrderRules"
        value="STATIC, STANDARD_JAVA_PACKAGE, SPECIAL_IMPORTS, THIRD_PARTY_PACKAGE"/>
      <property name="specialImportsRegExp" value="^org\."/>
      <property name="thirdPartyPackageRegExp" value="^com\."/>
      <property name="separateLineBetweenGroups" value="false"/>
      <property name="sortImportsInGroupAlphabetically" value="true"/>
    </module>
  </module>
</module>
*/

// xdoc section -- start
package com.puppycrawl.tools.checkstyle.checks.imports.customimportorder;

import static java.util.Collections.*;
import static java.io.File.separator; // violation, 'Wrong lexicographical'

import java.time.*;
import javax.net.*;
import org.apache.commons.io.FileUtils;
import com.puppycrawl.tools.checkstyle.checks.imports.CustomImportOrderCheck;
import com.puppycrawl.tools.checkstyle.checks.imports.ImportOrderCheck;
// xdoc section -- end
public class Example7 {
}
