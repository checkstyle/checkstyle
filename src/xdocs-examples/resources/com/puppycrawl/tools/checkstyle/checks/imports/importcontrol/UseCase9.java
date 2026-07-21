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

import java.awt.Image;     // violation, 'Disallowed import - java.awt.Image'
import java.io.File;       // violation, 'Disallowed import - java.io.File'
import java.io.FileReader; // violation, 'Disallowed import - java.io.FileReader'
import java.util.Date;     // violation, 'Disallowed import - java.util.Date'
import java.util.List;

public class UseCase9 {}
// xdoc section -- end
