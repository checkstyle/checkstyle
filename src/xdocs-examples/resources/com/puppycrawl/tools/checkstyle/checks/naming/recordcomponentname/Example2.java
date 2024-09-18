/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="RecordComponentName">
        <property name="format" value="^[a-z]+$"/>
    </module>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.naming.recordcomponentname;

// xdoc section -- start
public class Example2 {
    record MyRecord1(String value, int other) {} // ok
    record MyRecord2(String... strings) {} // ok
    record MyRecord3(double myNumber) {} // violation, myNumber should match '^[a-z]+$'
}
// xdoc section -- end
