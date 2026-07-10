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

import javax.swing.Action; // violation, 'Disallowed import - javax.swing.Action'

import java.awt.Image;     // violation, 'Disallowed import - java.awt.Image'
import java.util.Date;     // violation, 'Disallowed import - java.util.Date'
import java.util.List;     // violation, 'Disallowed import - java.util.List'
// violation below 'Disallowed import - java.util.stream.Collectors'
import java.util.stream.Collectors;
import java.util.stream.Stream;
// violation above 'Disallowed import - java.util.stream.Stream'
import sun.misc.Signal;    // violation, 'Disallowed import - sun.misc.Signal'

public class UseCase7 {}
// xdoc section -- end
