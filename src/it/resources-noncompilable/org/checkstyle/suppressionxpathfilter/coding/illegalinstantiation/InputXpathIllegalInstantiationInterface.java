// non-compiled with javac: but was compiled on jdk before 9, so we need to continue to support

package org.checkstyle.suppressionxpathfilter.coding.illegalinstantiation;

public class InputXpathIllegalInstantiationInterface {
    interface Inner {
        default void test() {
            Boolean x = new Boolean(true);
            Integer e = new Integer(5);
            String s = new String(); // warn
        }
    }
}
