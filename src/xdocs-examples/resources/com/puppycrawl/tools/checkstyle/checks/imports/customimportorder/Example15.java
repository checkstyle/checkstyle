/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="CustomImportOrder">
      <property name="customImportOrderRules"
        value="SAME_PACKAGE(3), THIRD_PARTY_PACKAGE, STATIC, SPECIAL_IMPORTS"/>
      <property name="specialImportsRegExp" value="^java\.lang\.String$"/>
    </module>
  </module>
</module>
*/

// xdoc section -- start
package com.puppycrawl.tools.checkstyle.checks.imports.customimportorder;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.Beta;
import com.google.common.annotations.VisibleForTesting;
import org.apache.commons.io.FileUtils;

import static java.util.Collections.emptyList;

import com.google.common.annotations.GwtCompatible; // violation, 'wrong order'

import java.lang.String;
// xdoc section -- end

public class Example15 {
}
