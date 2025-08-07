// non-compiled with javac: compiling on jdk before 9

package org.checkstyle.suppressionxpathfilter.coding.illegalinstantiation;

public class InputXpathIllegalInstantiationAnonymous {
    int b = 5; // ok
     class Inner{
        public void test() {
            Boolean x = new Boolean(true); // ok
            Integer e = new Integer(b); // warn
        }
     }
}
