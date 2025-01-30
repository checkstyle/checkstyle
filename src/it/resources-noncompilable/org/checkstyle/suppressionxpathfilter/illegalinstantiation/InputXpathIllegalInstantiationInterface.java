//non-compiled with javac: compiling on jdk before 9

package org.checkstyle.suppressionxpathfilter.illegalinstantiation;

public class InputXpathIllegalInstantiationInterface {
    interface Inner {
        default void test() {
            Boolean x = new Boolean(true);  
            Integer e = new Integer(5);  
            String s = new String(); // warn
        }
    }
}
