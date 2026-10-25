/*
IllegalInstantiation
classes = java.lang.Integer


*/

// non-compiled with javac: but was compiled on jdk before 9, so we need to continue to support

package org.checkstyle.suppressionxpathfilter.coding.illegalinstantiation;

public class InputXpathIllegalInstantiationAnonymous {
    int b = 5;
     class Inner{
        public void test() {
            Boolean x = new Boolean(true);
            // violation below 'Instantiation of java.lang.Integer should be avoided.'
            Integer e = new Integer(b);
        }
     }
}
