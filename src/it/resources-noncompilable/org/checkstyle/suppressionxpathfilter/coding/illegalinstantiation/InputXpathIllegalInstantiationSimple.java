/*
IllegalInstantiation
classes = java.lang.Boolean


*/

// non-compiled with javac: but was compiled on jdk before 9, so we need to continue to support

package org.checkstyle.suppressionxpathfilter.coding.illegalinstantiation;

public class InputXpathIllegalInstantiationSimple {
    int b = 5;
    public void test() {
        // violation below 'Instantiation of java.lang.Boolean should be avoided.'
        Boolean x = new Boolean(true);
        Integer e = new Integer(b);
    }
}
