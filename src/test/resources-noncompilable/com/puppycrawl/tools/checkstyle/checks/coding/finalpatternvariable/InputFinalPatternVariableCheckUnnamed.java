/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="FinalPatternVariable"/>
  </module>
</module>
*/

// non-compiled with javac: Compilable with Java22
package com.puppycrawl.tools.checkstyle.checks.coding.finalpatternvariable;

public class InputFinalPatternVariableCheckUnnamed {
    record Point(int x, int y) {}
    record Pair(Point p, int z) {}

    public void testUnnamed(Object obj) {
        if (obj instanceof String _) {
            System.out.println("unnamed type pattern");
        }
        if (obj instanceof Point(int _, int y)) {
            // violation above "Pattern variable 'y' should be declared final."
            System.out.println(y);
        }
        if (obj instanceof String s) {
            // violation above "Pattern variable 's' should be declared final."
            System.out.println(s);
        }
        if (obj instanceof final String _) {
            System.out.println("final unnamed pattern");
        }
        if (obj instanceof Pair(Point(int _, int y2), int _)) {
            // violation above "Pattern variable 'y2' should be declared final."
            System.out.println(y2);
        }
        if (obj instanceof Point(int _, int _)) {
            System.out.println("both unnamed");
        }
    }
}
