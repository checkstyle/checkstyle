/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="LambdaBodyLength">
      <property name="max" value="5"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.sizes.lambdabodylength;

// xdoc section -- start
class Example2 {
  // violation below 'Lambda body length is 10 lines (max allowed is 5).'
  Runnable r = () -> {
    System.out.println(2); // line 2 of lambda
    System.out.println(3);
    System.out.println(4);
    System.out.println(5);
    System.out.println(6);
    System.out.println(7);
    System.out.println(8);
    System.out.println(9);
  }; // line 10
  // violation below 'Lambda body length is 11 lines (max allowed is 5.'
  Runnable r2 = () -> {
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
  // violation below 'Lambda body length is 6 lines (max allowed is 5).'
  Runnable r3 = () ->
          "someString".concat("1")
                  .concat("2")
                  .concat("3")
                  .concat("4")
                  .concat("5")
                  .concat("6");
}
// xdoc section -- end
