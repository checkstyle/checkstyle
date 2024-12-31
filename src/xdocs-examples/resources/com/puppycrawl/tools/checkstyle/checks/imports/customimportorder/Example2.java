/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="CustomImportOrder">
      <property name="customImportOrderRules"
        value="STATIC, STANDARD_JAVA_PACKAGE, THIRD_PARTY_PACKAGE"/>
    </module>
  </module>
</module>
*/
// xdoc section -- start
package com.puppycrawl.tools.checkstyle.checks.imports.customimportorder;
import static java.util.Collections.*;

import java.time.*;
import javax.net.*;
import static java.io.File.separator; // violation, "wrong order"

import org.apache.commons.io.FileUtils;
import com.puppycrawl.tools.checkstyle.checks.imports.CustomImportOrderCheck;
import com.puppycrawl.tools.checkstyle.checks.imports.ImportOrderCheck;
// xdoc section -- end
public class Example2 {
}
