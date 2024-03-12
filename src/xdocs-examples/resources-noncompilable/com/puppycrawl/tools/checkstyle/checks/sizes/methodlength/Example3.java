/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="MethodLength">
      <property name="tokens" value="METHOD_DEF"/>
      <property name="max" value="4"/>
      <property name="countEmpty" value="false"/>
    </module>
  </module>
</module>
*/

//non-compiled with javac: Compilable with Java14
package com.puppycrawl.tools.checkstyle.checks.sizes.methodlength;

// xdoc section -- start
public class Example3 {

  // ok, CTOR_DEF is not in configured tokens
  public Example3()  {
    int var1 = 2;
    int var2 = 4;
    int sum = var1 + var2;
  }

  // ok, CTOR_DEF is not in configured tokens
  public Example3(int a)  {
    int var1 = 2;
    int sum = var1 + a;
  }

  // violation below, 'Method firstMethod length is 6 lines (max allowed is 4)'
  public void firstMethod() {
    int index = 0;
    if (index < 5) {
      index++;
    }
  }

  public void secondMethod() {
    // countEmpty property is false,so this line doesn't count
    System.out.println("line 3");
  }

  public void thirdMethod() {

    // countEmpty property is false,so this line and the line above don't count
    System.out.println("line 4");
  }

  record MyBadRecord() {
    // ok, COMPACT_CTOR_DEF is not in configured tokens
    public MyBadRecord {

      System.out.println("line3");
      System.out.println("line4");
    }
  }
}
// xdoc section -- end
