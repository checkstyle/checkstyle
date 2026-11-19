/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="FinalPatternVariable"/>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.coding.finalpatternvariable;

public class InputFinalPatternVariableCheck4 {
    private String str;

    record Pair(Object first, Object second) {}

    public void assignInCondition(Object obj) {
        if (obj instanceof String str && (str = "x").length() > 0) {
            System.out.println(str);
        }
    }

    public void compoundAssignInCondition(Object obj) {
        if (obj instanceof String str && (str += "x").length() > 0) {
            System.out.println(str);
        }
    }

    public void assignInElseIfCondition(Object obj) {
        if (obj == null) {
            System.out.println("none");
        }
        else if (obj instanceof String str && (str = "x").length() > 0) {
            System.out.println(str);
        }
    }

    public void assignInBody(Object obj) {
        if (obj instanceof String str) {
            str = "x";
            System.out.println(str);
        }
    }

    public void noReassignment(Object obj) {
        if (obj instanceof String str) {
            // violation above "Pattern variable 'str' should be declared final."
            System.out.println(str);
        }
    }

    public void fieldAssignedBeforeInstanceof(Object obj) {
        if ((str = "x").length() > 0 && obj instanceof String str) {
            // violation above "Pattern variable 'str' should be declared final."
            System.out.println(str);
        }
    }

    public void otherVariableAssignedInCondition(Object obj) {
        int count = 0;
        if (obj instanceof String str && (count = 1) > 0) {
            // violation above "Pattern variable 'str' should be declared final."
            System.out.println(str);
        }
        System.out.println(count);
    }

    public void ternaryCondition(Object obj) {
        String out = obj instanceof String str && (str = "x").length() > 0
                ? str : "none";
        System.out.println(out);
    }

    public void assignInConditionMultiline(Object obj) {
        if (obj instanceof String str
                && (str = "x").length() > 0) {
            System.out.println(str);
        }
    }

    public void fieldAssignedBeforeMultiline(Object obj) {
        if ((str = "x").length() > 0
                && obj instanceof String str) {
            // violation above "Pattern variable 'str' should be declared final."
            System.out.println(str);
        }
    }

    public void statementInBody(Object obj) {
        if (obj != null) {
            boolean matches = obj instanceof String str && (str = "x").length() > 0;
            System.out.println(matches);
        }
    }

    public void recordPatternInCondition(Object obj) {
        if (obj instanceof Pair(String str, Object other) && (str = "x").length() > 0) {
            // violation above "Pattern variable 'other' should be declared final."
            System.out.println(str);
            System.out.println(other);
        }
    }
}
