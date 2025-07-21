/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="ImportControl">
      <property name="file" value="${config.folder}/import-control12.xml"/>
    </module>
  </module>
</module>
*/

// xdoc section -- start
package com.puppycrawl.tools.checkstyle.checks.imports.importcontrol;

import java.util.Date; // violation, 'Disallowed import - java.util.Date'
import java.util.List;
import java.util.Optional;
import java.util.Map;

public class Example12 {}
// xdoc section -- end
