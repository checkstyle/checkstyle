/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="ImportControl">
      <property name="file" value="${config.folder}/import-control11.xml"/>
    </module>
  </module>
</module>
*/

// xdoc section -- start
package com.puppycrawl.tools.checkstyle.checks.imports.importcontrol.someImports;

import java.io.FileFilter;
import java.util.Date; // violation, 'Disallowed import - java.util.Date'
import java.util.List; // violation, 'Disallowed import - java.util.List'
import java.util.Optional;

import sun.misc.Signal; // violation, 'sun.misc.Signal'

public class Example11 {}
// xdoc section -- end
