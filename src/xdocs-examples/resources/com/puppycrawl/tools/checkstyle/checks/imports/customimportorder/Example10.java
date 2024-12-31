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

import static java.io.File.separator;
import static java.util.Collections.*;

import java.time.*; // violation, should be in standard package group

import javax.net.*; // violation, should be in special import group

import org.apache.commons.io.FileUtils; // violation, should be in THIRD PARTY PACKAGE GROUP

import com.puppycrawl.tools.checkstyle.checks.imports.CustomImportOrderCheck; // violation, 'wrong order'
import com.puppycrawl.tools.checkstyle.checks.imports.ImportOrderCheck; // violation, 'wrong order'
// xdoc section -- end
public class Example10 {
}
