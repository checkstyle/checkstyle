/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="RightCurlyAloneOrEmpty">
      <property name="tokens" value="LITERAL_SWITCH, LITERAL_CASE, CLASS_DEF, METHOD_DEF, ANNOTATION_DEF, ENUM_DEF"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.blocks.rightcurlyaloneorempty;

// xdoc section -- start
public class Example2 {
    public void test1() {
        int mode = 0;
        switch (mode) {
            case 1:{
                int x = 1;
                break;
            } default :          // violation '}' at column 13 should be alone on a line'
                int x = 0;
        }
        switch (mode) {} // ok, empty block is allowed
        switch (mode) { case 0: } // violation '}' at column 33 should be alone on a line'

    }

    public void test2() {
        if (true) {
            int x = 20;
        } else {
            int x = 10;
        }
    }

    public void test3() {} // ok, empty block is allowed

    public void test4() {
        int a = 10; } // violation '}' at column 21 should be alone on a line'

    public void test5() {int x = 10;} // violation '}' at column 37 should be alone on a line'

    public @interface TestAnnotation {} // ok, empty block is allowed

    public @interface TestAnnotation2 {
        String someValue(); }  // violation '}' at column 29 should be alone on a line'

    enum TestEnum1 {} // ok, empty block is allowed

    enum TestEnum2 {
    SOME_VALUE; } // violation '}' at column 17 should be alone on a line'

    enum TestEnum3 {} int x = 10; // violation '}' at column 21 should be alone on a line'

    public void test6() {
        try {

        }
        catch (Exception e) {}

        try {

        } catch (Exception e) {

        }
        finally {}

        try {

        } catch (Exception e) {
            int x = 10; }
    }
}
// xdoc section -- end
