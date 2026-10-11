/*xml
<module name="Checker">
  <property name="charset" value="US-ASCII"/>
  <module name="TreeWalker">
    <module name="NonEmptyAtclauseDescription"/>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.nonemptyatclausedescription;

public class InputNonEmptyAtclauseDescriptionDifferentCharset {

    /**
     * @author ü
     */
    private void foo() {
    }
}
