/*xml
<module name="Checker">
    <module name="SuppressWarningsFilter"/>
    <module name="TreeWalker">
        <module name="HiddenField"/>
        <module name="SuppressWarningsHolder">
            <property name="aliasList" value="com.puppycrawl.tools.checkstyle.checks.coding.HiddenFieldCheck=LocalVariableHidesMemberVariable"/>
        </module>
    </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.suppresswarningsholder;

public class InputSuppressWarningsHolderAlias3 {
    private int a;
    void withOriginal() {
      @SuppressWarnings({"hiddenfield"})
      int a = 1;
    }
    void withAlias() {
      @SuppressWarnings("LocalVariableHidesMemberVariable")
      int a = 1;
    }
}
