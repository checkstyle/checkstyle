/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="ImportControl">
      <property name="file" value="${config.folder}/import-control14.xml"/>
    </module>
  </module>
</module>
*/

// non-compiled with javac: Compilable with Java25
package com.puppycrawl.tools.checkstyle.checks.imports.importcontrol;

// xdoc section -- start
import module java.logging; // violation, 'Disallowed import - java.logging'
import module java.sql;     // violation, 'Disallowed import - java.sql'
import module java.xml;

public class Example14 {}
// xdoc section -- end
