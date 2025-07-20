/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="ImportControl">
      <property name="file" value="${config.folder}/import-control4.xml"/>
    </module>
  </module>
</module>
*/

// xdoc section -- start
package com.puppycrawl.tools.checkstyle.checks.imports.importcontrol.newdomain.dao;

import com.puppycrawl.tools.checkstyle.checks.TranslationCheck;
// violation above, 'Disallowed import'
import java.io.File;
import java.util.Scanner;
import javax.swing.ActionMap; // violation, 'Disallowed import - javax.swing'

public class Example4 {}
// xdoc section -- end
