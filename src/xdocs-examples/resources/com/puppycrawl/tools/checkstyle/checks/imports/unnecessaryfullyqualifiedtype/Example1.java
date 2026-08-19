/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="UnnecessaryFullyQualifiedType"/>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.imports.unnecessaryfullyqualifiedtype;

// xdoc section - start
import java.util.HashMap;
import java.util.Map;

class Example1 {

  // violation below 'Unnecessary fully qualified type - java.util.List.'
  java.util.List<String> list;

  Map<Boolean, String> choiceMap = new HashMap<>();

  private java.sql.Date sqlDate; // ok as two different 'Date' declared

  private java.util.Date utilDate; // ok as two different 'Date' declared

}
// xdoc section - end
