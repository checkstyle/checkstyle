/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="ImportControl">
      <property name="file" value="${config.folder}/import-control15.xml"/>
    </module>
  </module>
</module>
*/

// xdoc section - start
package com.puppycrawl.tools.checkstyle.checks.imports.importcontrol;

import java.io.File;
import java.util.ArrayList; // violation 'Disallowed import - java.util.ArrayList'

public class UseCase10 {}
// xdoc section - end
