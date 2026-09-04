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
        // Normal pattern variable, never reassigned, missing final
        if (o instanceof String s1) { // violation "Pattern variable 's1' should be declared final."
            System.out.println(s1);
        }

        // Normal pattern variable, explicitly final
        if (o instanceof final String s2) { // ok
            System.out.println(s2);
        }

        // Pattern variable that is reassigned
        if (o instanceof String s3) { // ok, reassigned so cannot be final
            s3 = "changed";
            System.out.println(s3);
        }

        // Same-name variable after scope
        if (o instanceof String s4) { // violation "Pattern variable 's4' should be declared final."
            System.out.println(s4);
        }
        String s5 = "normal"; // Not a pattern variable
        s5 = "changed";

        // Nested patterns
        record Point(int x, int y) {}
        record Rectangle(Point p1, Point p2) {}

        Object r = new Rectangle(new Point(1, 2), new Point(3, 4));
        if (r instanceof Rectangle(Point(int x, int y), // violation "Pattern variable 'x' should be declared final."
                Point p2)) {
            System.out.println(x);
            y = 10; // y is reassigned, so no warn for missing final on y
            p2 = null; // p2 is reassigned, so no warn for missing final on p2
        }
    }
}
