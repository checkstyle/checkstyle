/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="CustomImportOrder">
      <property name="customImportOrderRules"
        value="STATIC, STANDARD_JAVA_PACKAGE, SPECIAL_IMPORTS"/>
      <property name="specialImportsRegExp" value="^org\."/>
      <property name="sortImportsInGroupAlphabetically" value="true"/>
      <property name="separateLineBetweenGroups" value="true"/>
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
import org.apache.commons.io.FileUtils; // violation, 'should be separated'

import com.puppycrawl.tools.checkstyle.checks.imports.CustomImportOrderCheck;
import com.puppycrawl.tools.checkstyle.checks.imports.ImportOrderCheck;
// xdoc section -- end
public class Example8 {
}
