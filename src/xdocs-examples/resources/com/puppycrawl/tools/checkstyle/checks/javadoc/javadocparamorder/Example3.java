/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="JavadocParamOrder"/>
  </module>
</module>
*/


package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocparamorder;

// xdoc section -- start
public class Example3 {
    /**
     * Single type parameter in correct order.
     *
     * @param <T> the type parameter
     * @param value the value
     */
    public <T> void singleTypeParam(T value) {
    }

    /**
     * Multiple type parameters in correct order.
     *
     * @param <K> the key type
     * @param <V> the value type
     * @param key the key
     * @param value the value
     */
    public <K, V> void multipleTypeParams(K key, V value) {
    }

    /**
     * Type parameter after regular parameter (violation).
     *
     * @param value the value              // violation
     * @param <T> the type parameter      // violation
     */
    public <T> void typeParamWrong(T value) {
    }

    /**
     * Multiple type parameters with regular parameters, correct order.
     *
     * @param <K> the key type
     * @param <V> the value type
     * @param key the key
     * @param value the value
     * @param operation the operation
     */
    public <K, V> void complexMethod(K key, V value, String operation) {
    }
}
// xdoc section -- end



