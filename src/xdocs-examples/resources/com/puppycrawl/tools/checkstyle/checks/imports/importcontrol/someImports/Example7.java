/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="ImportControl">
      <property name="file" value="${config.folder}/import-control7.xml"/>
    </module>
  </module>
</module>
*/

// non-compiled with javac: Compilable with Java21
// xdoc section -- start
package com.puppycrawl.tools.checkstyle.checks.imports.importcontrol.someImports;

import javax.swing.Renderer;

import java.io.File;
import java.awt.Image; // violation, 'Disallowed import - java.awt.Image'

public class Example7 {}
// xdoc section -- end
