/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="ImportControl">
      <property name="file" value="${config.folder}/import-control1.xml"/>
    </module>
  </module>
</module>
*/

// xdoc section -- start
package com.puppycrawl.tools.checkstyle.checks.imports.importcontrol;

import java.io.File; // violation, 'Disallowed import - java.io.File'
import java.io.FileReader;

public class Example1 {}
// xdoc section -- end
