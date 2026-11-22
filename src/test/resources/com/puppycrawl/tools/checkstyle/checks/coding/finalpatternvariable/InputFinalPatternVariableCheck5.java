/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="FinalPatternVariable"/>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.coding.finalpatternvariable;

public class InputFinalPatternVariableCheck5 {
    private String str;

    public void fieldAssignedInInstanceofOperand(Object obj) {
        if ((str = "x") instanceof String str) {
            // violation above "Pattern variable 'str' should be declared final."
            System.out.println(str);
        }
    }

    public void fieldAssignedInInstanceofOperandNested(Object obj) {
        if (((str = "x") + (str = "y")) instanceof String str) {
            // violation above "Pattern variable 'str' should be declared final."
            System.out.println(str);
        }
    }

    public void patternVariableInElseBlock(Object obj) {
        if (obj == null) {
            System.out.println("none");
        }
        else {
            boolean matches = obj instanceof String str;
            // violation above "Pattern variable 'str' should be declared final."
            System.out.println(matches);
        }
    }
}
