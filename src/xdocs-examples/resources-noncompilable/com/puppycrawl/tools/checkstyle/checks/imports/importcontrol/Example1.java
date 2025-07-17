/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="ImportControl">
      <property name="file" value="${config.folder}/import-control1.xml"/>
    </module>
  </module>
</module>
*/

// non-compiled with javac: Compilable with Java17
// xdoc section -- start
package com.puppycrawl.tools.checkstyle.checks.imports.importcontrol;

import java.io.File; // violation, 'Disallowed import - java.util.stream.Stream'
import java.io.FileReader;

public class Example1 {}
// xdoc section -- end
