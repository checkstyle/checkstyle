/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="ImportControl">
      <property name="file" value="${config.folder}/import-control4.xml"/>
    </module>
  </module>
</module>
*/

// non-compiled with javac: Compilable with Java17
// xdoc section -- start
package com.puppycrawl.tools.checkstyle.checks.imports.importcontrol.newdomain.dao;

import com.puppycrawl.tools.checkstyle.checks.imports.importcontrol.ui;
// violation above, 'Disallowed import'
import java.io.File;
import java.util.Scanner;
import javax.swing; // violation, 'Disallowed import - javax.swing'

public class Example4 {}
// xdoc section -- end
