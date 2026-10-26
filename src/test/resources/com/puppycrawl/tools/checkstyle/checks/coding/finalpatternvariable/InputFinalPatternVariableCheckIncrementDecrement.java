/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="FinalPatternVariable"/>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.coding.finalpatternvariable;

public class InputFinalPatternVariableCheckIncrementDecrement {

    public void testPostIncrement(Object o) {
        if (o instanceof Integer i) {
            i++;
        }
    }

    public void testPostDecrement(Object o) {
        if (o instanceof Integer i) {
            i--;
        }
    }

    public void testPreIncrement(Object o) {
        if (o instanceof Integer i) {
            ++i;
        }
    }

    public void testPreDecrement(Object o) {
        if (o instanceof Integer i) {
            --i;
        }
    }

    public void testNoMutationShouldViolate(Object o) {
        if (o instanceof Integer i) { // violation "Pattern variable 'i' should be declared final."
            System.out.println(i);
        }
    }
}
