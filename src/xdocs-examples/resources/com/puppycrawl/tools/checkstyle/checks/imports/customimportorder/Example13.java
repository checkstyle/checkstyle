/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="CustomImportOrder">
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
import com.puppycrawl.tools.checkstyle.checks.imports.CustomImportOrderCheck; // OK
import com.puppycrawl.tools.checkstyle.checks.imports.ImportOrderCheck; // OK
import org.apache.commons.io.FileUtils; // OK
// xdoc section -- end
public class Example13 {
}
