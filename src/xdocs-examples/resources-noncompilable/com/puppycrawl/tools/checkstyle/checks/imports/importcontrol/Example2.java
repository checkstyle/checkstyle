/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="ImportControl">
      <property name="file" value="${config.folder}/import-control2.xml"/>
      <property name="path" value="${config.folder}[\\/]api[\\/].*$"/>
    </module>
  </module>
</module>
*/

// non-compiled with javac: Compilable with Java17
// xdoc section -- start
package com.puppycrawl.tools.checkstyle.checks.imports.importcontrol;

import java.io.File; // ok, import control does not cover this path
import java.io.FileReader;

public class Example2 {}
// xdoc section -- end
