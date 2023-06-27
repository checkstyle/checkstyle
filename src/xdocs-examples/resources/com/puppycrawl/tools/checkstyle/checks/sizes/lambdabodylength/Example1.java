/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="LambdaBodyLength"/>
  </module>
</module>


*/

package com.puppycrawl.tools.checkstyle.checks.sizes.lambdabodylength;

// xdoc section -- start
class Example1 {
  Runnable r = () -> { // OK, length is 10
    System.out.println(2); // line 2 of lambda
    System.out.println(3);
    System.out.println(4);
    System.out.println(5);
    System.out.println(6);
    System.out.println(7);
    System.out.println(8);
    System.out.println(9);
  }; // line 10
  Runnable r2 = () -> { // violation 'length is 11'
    System.out.println(2); // line 2 of lambda
    System.out.println(3);
    System.out.println(4);
    System.out.println(5);
    System.out.println(6);
    System.out.println(7);
    System.out.println(8);
    System.out.println(9);
    System.out.println(10);
  }; // line 11
  Runnable r3 = () -> // violation 'length is 11'
    "someString".concat("1") // line 1 of lambda
                .concat("2")
                .concat("3")
                .concat("4")
                .concat("5")
                .concat("6")
                .concat("7")
                .concat("8")
                .concat("9")
                .concat("10")
                .concat("11"); // line 11
}
// xdoc section -- end
