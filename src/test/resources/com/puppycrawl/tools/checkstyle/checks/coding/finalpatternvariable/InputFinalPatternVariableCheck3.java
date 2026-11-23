/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="FinalPatternVariable"/>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.coding.finalpatternvariable;

public class InputFinalPatternVariableCheck3 {
    record Pair(Object first, Object second) {}

    public void siblingSameName(Object obj) {
        if (obj instanceof String str) {
            // violation above "Pattern variable 'str' should be declared final."
            System.out.println(str);
        }
        if (obj instanceof String str) {
            str = "new value";
            System.out.println(str);
        }
    }

    public void siblingDifferentName(Object obj) {
        if (obj instanceof String str) {
            // violation above "Pattern variable 'str' should be declared final."
            System.out.println(str);
        }
        if (obj instanceof String text) {
            text = "new value";
            System.out.println(text);
        }
    }

    public void siblingSameNameNoReassign(Object obj) {
        if (obj instanceof String str) {
            // violation above "Pattern variable 'str' should be declared final."
            System.out.println(str);
        }
        if (obj instanceof String str) {
            // violation above "Pattern variable 'str' should be declared final."
            System.out.println(str);
        }
    }

    public void threeSiblings(Object obj) {
        if (obj instanceof String str) {
            // violation above "Pattern variable 'str' should be declared final."
            System.out.println(str);
        }
        if (obj instanceof String str) {
            str = "new value";
            System.out.println(str);
        }
        if (obj instanceof String str) {
            // violation above "Pattern variable 'str' should be declared final."
            System.out.println(str);
        }
    }

    public void siblingWithoutRedeclaration(Object obj, boolean flag) {
        if (!(obj instanceof String str)) {
            return;
        }
        if (flag) {
            str = "new value";
        }
        System.out.println(str);
    }

    public void siblingOtherNameAssignsOurs(Object obj, Object obj2) {
        if (!(obj instanceof String str)) {
            return;
        }
        if (obj2 instanceof String text) {
            // violation above "Pattern variable 'text' should be declared final."
            str = "new value";
            System.out.println(text);
        }
        System.out.println(str);
    }

    public void whileInSiblingIf(Object obj, Object obj2, boolean flag) {
        if (obj instanceof String str) {
            // violation above "Pattern variable 'str' should be declared final."
            System.out.println(str);
        }
        if (flag) {
            while (obj2 instanceof String str) {
                str = "new value";
                break;
            }
        }
    }

    public void siblingRecordPattern(Object obj) {
        if (obj instanceof String str) {
            // violation above "Pattern variable 'str' should be declared final."
            System.out.println(str);
        }
        if (obj instanceof Pair(String str, Object other)) {
            // violation above "Pattern variable 'other' should be declared final."
            str = "new value";
            System.out.println(str);
        }
    }
}
