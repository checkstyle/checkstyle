/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="FinalPatternVariable"/>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.coding.finalpatternvariable;

public class InputFinalPatternVariableCheck {

    public void run(Object o) {
        if (o instanceof String s1) { // violation "Pattern variable 's1' should be declared final."
            System.out.println(s1);
        }
        if (o instanceof final String s2) { // ok, explicitly final
            System.out.println(s2);
        }
        if (o instanceof String s3) { // ok, reassigned so cannot be final
            s3 = "changed";
            System.out.println(s3);
        }
        if (o instanceof String s4) { // violation "Pattern variable 's4' should be declared final."
            System.out.println(s4);
        }
        String s5 = "normal"; // Not a pattern variable
        s5 = "changed";
        record P(int x, int y) {}
        record Rec(P p1, P p2) {}
        Object r = new Rec(new P(1, 2), new P(3, 4));
        if (r instanceof Rec(P(int x, int y), P p2)) {
            // violation above "Pattern variable 'x' should be declared final."
            System.out.println(x);
            y = 10; // y is reassigned, so no warn for missing final on y
            p2 = null; // p2 is reassigned, so no warn for missing final on p2
        }

        if (o instanceof Integer i1) {
            // violation above "Pattern variable 'i1' should be declared final."
            System.out.println(i1);
        }
        else if (o instanceof String s6) {
            // violation above "Pattern variable 's6' should be declared final."
            System.out.println(s6);
        }

        int len = (o instanceof String s7) ? s7.length() : 0;
        // violation above "Pattern variable 's7' should be declared final."

        if (o instanceof String s8) s8 = "b"; // ok, reassigned
        if (!(o instanceof String s9)) {
            return;
        }
        s9 = "c"; // ok, reassigned

        boolean b = o instanceof String s10;
        // violation above "Pattern variable 's10' should be declared final."

        if (!(o instanceof String s11)) return; ; s11 = "d";
        if (!(o instanceof String s12)) return;
        // violation above "Pattern variable 's12' should be declared final."
        if (s12.isEmpty()) {}

        if (o == null) {} else {
            if (o instanceof String s13) System.out.println(s13);
            // violation above "Pattern variable 's13' should be declared final."
        }

        if (o instanceof String) {}
        if (!(o instanceof String s14)) return;
        // violation above "Pattern variable 's14' should be declared final."
        return;
    }

    public void checkElse(Object o) {
        if (o == null) return; else System.out.println(o instanceof String s15);
        // violation above "Pattern variable 's15' should be declared final."
    }

    public void testPitestMutations(Object o, boolean cond) {
        if (cond) {
            String s16 = "new";
        } else if (o instanceof String s16) {
            // violation above "Pattern variable 's16' should be declared final."
        }

        record Pair(Object first, Object second) {}
        Object pairObj = new Pair("a", "b");
        if (pairObj instanceof Pair(final Object f1, Object s17)) {
            // violation above "Pattern variable 's17' should be declared final."
        }

        int len2 = (o instanceof String s18) ? (s18 = "reassigned").length() : 0;

        if (o == null) {
        } else if (o instanceof String s19) {
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
    }
}
