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
public class Example1 {
    record MyRecord1(String value, int otherComponentName) {} // violation, otherComponentName should match '^[a-z]+$'
    record MyRecord2(String... Values) {} // violation, Values should match '^[a-z]+$'
    record MyRecord3(double my_number) {} // violation, my_number should match '^[a-z]+$'
}
// xdoc section -- end
