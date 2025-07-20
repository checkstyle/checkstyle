/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="ImportControl">
      <property name="file" value="${config.folder}/import-control9.xml"/>
    </module>
  </module>
</module>
*/

// xdoc section -- start
package com.puppycrawl.tools.checkstyle.checks.imports.importcontrol.filters;

import java.util.Date;
import java.util.List;
import java.util.Map; // violation, 'Disallowed import - java.util.Map'

public class Example9 {}
// xdoc section -- end
