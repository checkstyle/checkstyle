/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="FinalPatternVariable"/>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.coding.finalpatternvariable;

public class InputFinalPatternVariableCheck2 {
    record Pair(Object first, Object second) {}

    private String s23, s27, s30;

    public void testPitestMutations(Object o, boolean cond) {
        if (cond) {
            String s16 = "new";
        }
        else if (o instanceof String s16) {
            // violation above "Pattern variable 's16' should be declared final."
        }

        Object pairObj = new Pair("a", "b");
        if (pairObj instanceof Pair(final Object f1, Object s17)) {
            // violation above "Pattern variable 's17' should be declared final."
        }

        int len2 = (o instanceof String s18) ? (s18 = "reassigned").length() : 0;

        if (o != null) {}
        else if (o instanceof String s19) {
            s19 = "reassigned in else-if";
        }

        if (!(o instanceof String s20)) return;
        // violation above "Pattern variable 's20' should be declared final."
        int dummyVar = 0;
        s20 = "after dummy var";

        if (!(o instanceof String s21)) return;
        s21 = "reassigned after guard";

        if (!(o instanceof String s22)) return;
        if (s22.isEmpty()) return;
        s22 = "reassigned after if";

        if (o == null) {
        } else {
            boolean b = o instanceof String s23;
            s23 = "else scope";
        }

        if (!(o instanceof String s24)) return;
        if (s24.isEmpty()) {
            s24 = "reassigned inside if";
        }

        if (!(o instanceof String s26)) return;
        ;
        s26 = "reassigned after empty stat";

        if (o instanceof String s27) {
            // violation above "Pattern variable 's27' should be declared final."
        } else {
            s27 = "reassigned in else";
        }

        if (!(o instanceof String s29)) {
            // violation above "Pattern variable 's29' should be declared final."
            return;
        }
        while (o != null) {
            s29 = "reassigned in while";
        }

        if (!(o instanceof String s30)) {
            // violation above "Pattern variable 's30' should be declared final."
            return;
        }
        int dummy = 0;
        s30 = "reassigned after dummy";
    }

    public String checkReturn(Object o) {
        if (!(o instanceof String s25)) return "";
        return s25 = "reassigned inside return";
    }

    public void checkCompoundAssign(Object o) {
        if (o instanceof String s31) {
            s31 += "tail";
        }
    }
}
