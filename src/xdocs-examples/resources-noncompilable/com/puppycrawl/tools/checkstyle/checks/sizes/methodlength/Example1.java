/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="MethodLength">
      <property name="max" value="4"/>
    </module>
  </module>
</module>
*/

//non-compiled with javac: Compilable with Java14
package com.puppycrawl.tools.checkstyle.checks.sizes.methodlength;

// xdoc section -- start
public class Example1 {

  // violation below, 'Method Example1 length is 5 lines (max allowed is 4)'
  public Example1() {
    int var1 = 2;
    int var2 = 4;
    int sum = var1 + var2;
  }

  public Example1(int a)  {
    int var1 = 2;
    int sum = var1 + a;
  } // line 4, OK, as it allows at most 4 lines

  // violation below, 'Method firstMethod length is 6 lines (max allowed is 4)'
  public void firstMethod() {
    int index = 0;
    if (index < 5) {
      index++;
    }
  }

  public void secondMethod() {
    // comment
    System.out.println("line 3");
  } // line 4, OK, as it allows at most 4 lines

  record MyTestRecord() {
    //violation below,'Method MyTestRecord length is 5 lines (max allowed is 4)'
    public MyTestRecord {

      System.out.println("test");
      System.out.println("test");
    }
  }

  record MyTestRecord() {

    public MyTestRecord {
      System.out.println("test");
    } // line 3, OK, as it allows at most 4 lines
  }

}
// xdoc section -- end
