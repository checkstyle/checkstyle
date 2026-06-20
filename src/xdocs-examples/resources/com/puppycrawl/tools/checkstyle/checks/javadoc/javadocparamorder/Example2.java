/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="JavadocParamOrder"/>
  </module>
</module>
*/


package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocparamorder;

// xdoc section -- start
public class Example2 {
    /**
     * Two parameters out of order.
     *
     * @param second the second parameter // violation
     * @param first the first parameter   // violation
     */
    public void twoParamsWrong(String first, String second) {
    }

    /**
     * Three parameters out of order.
     *
     * @param third the third parameter   // violation
     * @param first the first parameter   // violation
     * @param second the second parameter // violation
     */
    public void threeParamsWrong(String first, String second, String third) {
    }

    /**
     * Constructor with parameters out of order.
     *
     * @param age the person's age     // violation
     * @param name the person's name   // violation
     */
    public Example2(String name, int age) {
    }
}
// xdoc section -- end





