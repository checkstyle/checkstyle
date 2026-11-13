/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="ImportOrder">
      <property name="useContainerOrdering" value="false"/>
      <property name="ordered" value="true"/>
      <property name="caseSensitive" value="false"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.imports.importorder;

// xdoc section - start
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
// violation above 'Wrong lexicographical order for ...'
// xdoc section - end

public class Example14 { }
