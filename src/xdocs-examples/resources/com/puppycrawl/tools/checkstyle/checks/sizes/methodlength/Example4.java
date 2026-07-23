/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="MethodLength"/>
  </module>
</module>
*/

// Java17
package com.puppycrawl.tools.checkstyle.checks.sizes.methodlength;

// xdoc section -- start
public class Example4 {

  // ok, default max is 150 lines
  public Example4() {
    int var1 = 2;
    int var2 = 4;
    int sum = var1 + var2;
  }

  // ok, default max is 150 lines
  public Example4(int a)  {
    int var1 = 2;
    int sum = var1 + a;
  }

  // ok, default max is 150 lines
  public void firstMethod() {
    int index = 0;
    if (index < 5) {
      index++;
    }
  }

  public void secondMethod() {

    System.out.println("line 3");
  }

  public void thirdMethod() {

    // ok, empty line above is counted by default, just like this comment
    System.out.println("line 4");
  }

  record MyBadRecord() {

    public MyBadRecord {

      System.out.println("line3");
      System.out.println("line4");
    }
  }
}
// xdoc section -- end
