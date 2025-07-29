/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="ImportControl">
      <property name="file" value="${config.folder}/import-control5.xml"/>
    </module>
  </module>
</module>
*/

// xdoc section -- start
package com.puppycrawl.tools.checkstyle.checks.imports.importcontrol;

import java.awt.Image; // violation, 'Disallowed import - java.awt.Image'
import java.io.File; // violation, 'Disallowed import - java.io.File'

import java.util.Scanner; // ok, allowed on mismatch by default

public class Example5 {}
// xdoc section -- end
