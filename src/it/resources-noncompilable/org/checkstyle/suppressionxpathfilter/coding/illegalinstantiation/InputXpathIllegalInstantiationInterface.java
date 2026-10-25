/*
IllegalInstantiation
classes = java.lang.String


*/

// non-compiled with javac: but was compiled on jdk before 9, so we need to continue to support

package org.checkstyle.suppressionxpathfilter.coding.illegalinstantiation;

public class InputXpathIllegalInstantiationInterface {
    interface Inner {
        default void test() {
            Boolean x = new Boolean(true);
            Integer e = new Integer(5);
            // violation below 'Instantiation of java.lang.String should be avoided.'
            String s = new String();
        }
    }
}
