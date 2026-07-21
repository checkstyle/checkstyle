/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.internal.testmodules.CheckWhichThrowsError" />
  </module>
  <property name="haltOnException" value="false"/>
</module>
*/
// violation first line 'java.lang.IndexOutOfBoundsException: test'

package com.puppycrawl.tools.checkstyle.checker;
/*comment*/
public class InputChecker {
}
class InputCheckerInner {
}
