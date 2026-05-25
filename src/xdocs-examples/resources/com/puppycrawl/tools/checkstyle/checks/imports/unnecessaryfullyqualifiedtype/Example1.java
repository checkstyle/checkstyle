/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="UnnecessaryFullyQualifiedType"/>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.imports.unnecessaryfullyqualifiedtype;

import java.util.HashMap;
import java.util.Map;

// xdoc section -- start
class Example1 {

  // violation below, 'Unnecessary fully qualified type - java.util.List.'
  java.util.List<String> list;

  Map<Boolean, String> choiceMap = new HashMap<>();
}
// xdoc section -- end
