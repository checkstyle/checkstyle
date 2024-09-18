/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="RecordTypeParameterName"/>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.naming.recordtypeparametername;

// xdoc section -- start
public class Example1 {
    record MyRecord1<T>() {} // OK
    record MyRecord2<t>() {} // violation, the record type parameter
    record MyRecord3<abc>() {} // violation, the record type parameter
}
// xdoc section -- end
