/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="ImportControl">
      <property name="file" value="${config.folder}/import-control6.xml"/>
    </module>
  </module>
</module>
*/

// non-compiled with javac: Compilable with Java17
// xdoc section -- start
package com.puppycrawl.tools.checkstyle.checks.imports.importcontrol.someImports;

import javax.swing;

import java.io.File;
import java.util.Random; // violation, 'Disallowed import - java.util.Random'

public class Example6 {}
// xdoc section -- end
