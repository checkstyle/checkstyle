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

// xdoc section -- start
package com.puppycrawl.tools.checkstyle.checks.imports.customimportorder;

import static java.io.File.separator; // OK
import static java.util.Collections.*; // OK

import java.time.*; // violation, "wrong order"

import javax.net.*; // violation, "wrong order"

import org.apache.commons.io.FileUtils; // violation, "wrong order"

import com.puppycrawl.tools.checkstyle.checks.imports.CustomImportOrderCheck; // violation, "wrong order"
import com.puppycrawl.tools.checkstyle.checks.imports.ImportOrderCheck; // violation, "wrong order"
// xdoc section -- end
public class Example10 {
}
