/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="RightCurlyAloneOrEmpty"/>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.blocks.rightcurlyaloneorempty;

// xdoc section -- start
public class Example1 {
    public void test1() {
        int mode = 0;
        switch (mode) {
            case 1:{
                int x = 1;
                break;
            } default :
                int x = 0;
        }
        switch (mode) {}
        switch (mode) { case 0: }

    }

    public void test2() {
        if (true) {
            int x = 20;
        } else { // violation '}' at column 9 should be alone on a line'
            int x = 10;
        }
    }

    public void test3() {}

    public void test4() {
        int a = 10; }

    public void test5() {int x = 10;}

    public @interface TestAnnotation {}

    public @interface TestAnnotation2 {
        String someValue(); }

    enum TestEnum1 {}

    enum TestEnum2 {
    SOME_VALUE; }

    enum TestEnum3 {} int x = 10;

    public void test6() {
        try {

        }
        catch (Exception e) {} // violation '}' at column 30 should be alone on a line'

        try {

        } catch (Exception e) { // violation '}' at column 9 should be alone on a line'

        }
        finally {} // violation '}' at column 18 should be alone on a line'

        try {

        } catch (Exception e) { // violation '}' at column 9 should be alone on a line'
            int x = 10; } // violation '}' at column 25 should be alone on a line'
    }
}
// xdoc section -- end
