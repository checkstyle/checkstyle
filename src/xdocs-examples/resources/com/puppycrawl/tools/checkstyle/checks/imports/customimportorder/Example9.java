/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="CustomImportOrder">
      <property name="customImportOrderRules"
        value="STATIC, STANDARD_JAVA_PACKAGE, SPECIAL_IMPORTS, THIRD_PARTY_PACKAGE"/>
      <property name="specialImportsRegExp" value="^org\."/>
      <property name="thirdPartyPackageRegExp" value="^com\."/>
      <property name="sortImportsInGroupAlphabetically" value="true"/>
      <property name="separateLineBetweenGroups" value="true"/>
    </module>
  </module>
</module>
*/

// xdoc section -- start
package com.puppycrawl.tools.checkstyle.checks.imports.customimportorder;

import static java.io.File.separator; // OK
import static java.util.Collections.*; // OK

import java.time.*; // OK
import javax.net.*; // OK

import com.puppycrawl.tools.checkstyle.checks.imports.CustomImportOrderCheck; // violation, "wrong order"
import com.puppycrawl.tools.checkstyle.checks.imports.ImportOrderCheck; // violation, "wrong order"

import org.apache.commons.io.FileUtils;
// xdoc section -- end

public class Example9 {
}
