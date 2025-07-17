/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="ImportControl">
      <property name="file" value="${config.folder}/import-control10.xml"/>
      <property name="path" value="^.*[\\/]src[\\/]xdocs-examples[\\/].*$"/>
    </module>
  </module>
</module>
*/

// non-compiled with javac: Compilable with Java17
// xdoc section -- start
package com.puppycrawl.tools.checkstyle.checks.imports.importcontrol.someImports;

import java.util.stream.Stream;     // violation
import java.util.stream.Collectors; // violation
import java.util.stream.IntStream;

public class Example10 {}
// xdoc section -- end
