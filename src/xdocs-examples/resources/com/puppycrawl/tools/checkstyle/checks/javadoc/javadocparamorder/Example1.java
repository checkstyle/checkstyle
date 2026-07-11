/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="JavadocParamOrder"/>
  </module>
</module>
*/


package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocparamorder;

// xdoc section -- start
public class Example1 {
    /**
     * Two parameters in correct order.
     *
     * @param first the first parameter
     * @param second the second parameter
     */
    public void twoParams(String first, String second) {
    }

    /**
     * Three parameters in correct order.
     *
     * @param first the first parameter
     * @param second the second parameter
     * @param third the third parameter
     */
    public void threeParams(String first, String second, String third) {
    }

    /**
     * Multiple parameters with partial documentation (no violation).
     *
     * @param first the first parameter
     * @param third the third parameter
     */
    public void partialDocs(String first, String second, String third) {
    }
}
// xdoc section -- end





