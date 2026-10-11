// non-compiled with javac: but was compiled on jdk before 9, so we need to continue to support

package org.checkstyle.suppressionxpathfilter.coding.illegalinstantiation;

public class InputXpathIllegalInstantiationSimple {
    int b = 5;
    public void test() {
        Boolean x = new Boolean(true); // warn
        Integer e = new Integer(b);
    }
}
