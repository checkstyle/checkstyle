/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="InnerAssignment"/>
  </module>
</module>
*/

//non-compiled with javac: Compilable with Java17
package com.puppycrawl.tools.checkstyle.checks.coding.innerassignment;


// xdoc section -- start
public class Example2 {
  public void test1(int mode) {
    int x = 0;
    x = switch (mode) {
      case 1 -> x = 1;  // violation
      case 2 -> {
        yield x = 2; // violation
      }
      default -> x = 0; // violation
    };
  }
  public void test2(int mode) {
    int x = 0;
    switch(mode) {
      case 2 -> {
        x = 2;
      }
      case 1 -> x = 1;
    }
  }
  public void test3(int mode) {
    int x = 0, y = 0;
    switch(mode) {
      case 1:
      case 2: {
        x = y = 2; // violation
      }
      case 4:
      case 5:
        x = 1;
    }
  }
}
// xdoc section -- end
