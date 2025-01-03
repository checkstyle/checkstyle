/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="CustomImportOrder">
      <property name="customImportOrderRules"
        value="STATIC, SPECIAL_IMPORTS, STANDARD_JAVA_PACKAGE"/>
      <property name="specialImportsRegExp" value="^org\."/>
    </module>
  </module>
</module>
*/

// xdoc section -- start
package com.puppycrawl.tools.checkstyle.checks.imports.customimportorder;

import static java.util.Collections.*;
import static java.io.File.separator;

import org.apache.commons.lang3.StringUtils;

import java.time.*;
import javax.net.*;

import org.apache.commons.io.FileUtils; // violation, 'wrong order'
// xdoc section -- end

public class Example5 {
}
