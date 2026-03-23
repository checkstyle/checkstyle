/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="RightCurlyAloneOrEmpty">
      <property name="tokens" value="LITERAL_SWITCH, LITERAL_CASE, CLASS_DEF,
      METHOD_DEF, ANNOTATION_DEF, ENUM_DEF"/>
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
      case 1 -> {
        int x = 1; }
      // violation above ''}' at column 20 should be alone on a line'
      default -> {
        int x = 0;
      }
    }

    switch (mode) {
      case 0 -> { }
      // ok above, empty block
      default -> { int x = 0; }
    }
  }

  public void test2() {}
  // ok above, empty block is allowed

  public void test3() {
    int a = 10; }
  // violation above ''}' at column 17 should be alone on a line'

  public void test4() {int x = 10;}
  // violation above ''}' at column 35 should be alone on a line'

  public @interface TestAnnotation {}
  // ok, above empty block is allowed

  public @interface TestAnnotation2 {
    String someValue(); }
  // violation above ''}' at column 25 should be alone on a line'

  enum TestEnum1 {}
  // ok above, empty block is allowed

  enum TestEnum2 {
  SOME_VALUE; } // violation ''}' at column 15 should be alone on a line'

  enum TestEnum3 {}
}
// xdoc section -- end
