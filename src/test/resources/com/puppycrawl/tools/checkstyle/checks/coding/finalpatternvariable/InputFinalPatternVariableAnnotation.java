/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="FinalPatternVariable"/>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.coding.finalpatternvariable;

public class InputFinalPatternVariableAnnotation {

    @interface Inner { String val(); }
    @interface Outer { Inner inner(); }
    @interface Multi { String a(); int b(); }
    @interface ArrayAnno { String[] names(); }

    public void test(Object obj) {
        if (obj instanceof String str) {
            // violation above "Pattern variable 'str' should be declared final."
            @SuppressWarnings(value = "unused")
            int y = str.length();
        }
        if (obj instanceof Integer num) {
            // violation above "Pattern variable 'num' should be declared final."
            @Deprecated(since = "1.0")
            int z = num;
        }
        if (obj instanceof String s1) {
            // violation above "Pattern variable 's1' should be declared final."
            @Multi(a = "x", b = 1)
            int v1 = 0;
            @Outer(inner = @Inner(val = "nested"))
            int v2 = 0;
            @ArrayAnno(names = {"a", "b"})
            int v3 = 0;
            System.out.println(s1);
        }
        if (obj instanceof String s2) {
            @Multi(a = "y", b = 2)
            int v4 = 0;
            s2 = "reassigned";
            System.out.println(s2);
        }
    }
}
