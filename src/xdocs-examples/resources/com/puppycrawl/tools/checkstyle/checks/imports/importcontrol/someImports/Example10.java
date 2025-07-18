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

// xdoc section -- start
package com.puppycrawl.tools.checkstyle.checks.imports.importcontrol.someImports;

import java.util.stream.Stream;     // violation, 'Disallowed import - java.util.stream.Stream'
import java.util.stream.Collectors; // violation, 'Disallowed import - java.util.stream.Collectors'
import java.util.stream.IntStream;

public class Example10 {}
// xdoc section -- end
