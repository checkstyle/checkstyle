/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="ImportControl">
      <property name="file" value="${config.folder}/import-control8.xml"/>
    </module>
  </module>
</module>
*/

// xdoc section -- start
package com.puppycrawl.tools.checkstyle.checks.imports.importcontrol.gui;

import java.sql.Blob; // violation, 'Disallowed import - java.sql.Blob'
import java.io.File;

import javax.swing.Renderer; // ok, does not match a file name for disallow rule.

public class Example8 {}
// xdoc section -- end
