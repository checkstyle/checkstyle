//non-compiled with javac: compiling on jdk before 9

package org.checkstyle.suppressionxpathfilter.illegalinstantiation;

public class SuppressionXpathRegressionIllegalInstantiationInterface {
    interface Inner {
        default void test() {
            Boolean x = new Boolean(true); // ok
            Integer e = new Integer(5); // ok
            String s = new String(); // warn
        }
    }
}
