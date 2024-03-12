//non-compiled with javac: compiling on jdk before 9

package org.checkstyle.suppressionxpathfilter.illegalinstantiation;

public class SuppressionXpathRegressionIllegalInstantiationSimple {
    int b = 5; // ok
    public void test() {
        Boolean x = new Boolean(true); // warn
        Integer e = new Integer(b); // ok
    }
}
