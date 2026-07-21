// non-compiled with javac: but was compiled on jdk before 9, so we need to continue to support

package org.checkstyle.suppressionxpathfilter.coding.illegalinstantiation;

public class InputXpathIllegalInstantiationAnonymous {
    int b = 5;
     class Inner{
        public void test() {
            Boolean x = new Boolean(true);
            Integer e = new Integer(b); // warn
        }
     }
}
