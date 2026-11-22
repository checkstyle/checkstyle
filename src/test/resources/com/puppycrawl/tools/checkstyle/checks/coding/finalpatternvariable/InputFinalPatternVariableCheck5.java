/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="FinalPatternVariable"/>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.coding.finalpatternvariable;

public class InputFinalPatternVariableCheck5 {

    public void assignmentBeforeNestedIf(Object obj, Object obj2, boolean flag) {
        if (obj instanceof String str) {
            if (flag) {
                str = "new value";
                Object o = new Object() {
                    void nested(Object obj2) {
                        if (obj2 instanceof String str) {
                            // violation above "Pattern variable 'str' should be declared final."
                            System.out.println(str);
                        }
                    }
                };
                System.out.println(o);
            }
            System.out.println(str);
        }
    }

    public void whileCoveringWithInnerDef(Object obj, Object obj2, boolean flag) {
        if (flag) {
            while (obj instanceof String str) {
                Object o = new Object() {
                    void nested(Object obj2) {
                        if (obj2 instanceof String str) {
                            // violation above "Pattern variable 'str' should be declared final."
                            System.out.println(str);
                        }
                    }
                };
                System.out.println(o);
                str = "new value";
                break;
            }
        }
    }

    public void sameNameDefInAnonymousClassCondition(Object obj, Object obj2) {
        if (obj instanceof String str && new Object() {
            boolean test(Object o) {
                return o instanceof String str;
                // violation above "Pattern variable 'str' should be declared final."
            }
        }.test(obj2)) {
            str = "new value";
            System.out.println(str);
        }
    }
}
