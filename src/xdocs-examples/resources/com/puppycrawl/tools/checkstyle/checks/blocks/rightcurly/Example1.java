/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="RightCurly"/>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.blocks.rightcurly;

// xdoc section -- start
public class Example1 {

  public void test() {

    boolean foo = false;
    if (foo) {
      bar();
    } // violation, 'should be on the same line'
    // as the next part of a multi-block statement (one that directly
    // contains multiple blocks: if/else-if/else, do/while or try/catch/finally).
    else { // ok
      bar();
    }

    if (foo) {
      bar();
    } else {
      bar();
    }

    if (foo) { bar(); } int i = 0;
    // violation above, 'should be alone on a line.'

    if (foo) { bar(); }
    i = 0;

    try {
      bar();
    } // violation, 'should be on the same line'
    // as the next part of a multi-block statement (one that directly
    // contains multiple blocks: if/else-if/else, do/while or try/catch/finally).
    catch (Exception e) { // ok because config did not set token LITERAL_TRY
      bar();
    }

    try {
      bar();
    } catch (Exception e) {
      bar();
    }

  }

  private void bar() {
  }

  public void testSingleLine() { bar(); } // ok, because singleline is allowed

  public void violate() { Object bar = "bar"; }
  // ok

  public void method0() {
    int mode = 0;
    int x;
    switch (mode) {
      case 1:
        int y = 1;
        break;
      case 2: {x = 1;}   // ok
      case 3: int z = 0; {break;} // ok, the braces is not a first child of case
      default:
        x = 0;
    } // ok, RightCurly is alone
  }

  public void method01() {
    int mode = 0;
    switch (mode) {
      case 1:
        int x = 1;
        break;
      default:
        x = 0; }
    // ok
  }

  public static void method7() {
    int mode = 0;
    switch (mode) {
      case 1:
        int x = 5;
    } // ok, RightCurly is on the same line as LeftCurly
  }

  public void method() {
    int mode = 0;
    int x;
    switch (mode) {
      case 1:
        x = 1; }
    // ok
  }
}
// xdoc section -- end
