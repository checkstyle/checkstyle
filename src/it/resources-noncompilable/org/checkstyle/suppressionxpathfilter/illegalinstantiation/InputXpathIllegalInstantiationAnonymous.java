//non-compiled with javac: compiling on jdk before 9

package org.checkstyle.suppressionxpathfilter.illegalinstantiation;

public class InputXpathIllegalInstantiationAnonymous {
    int b = 5;  
     class Inner{
        public void test() {
            Boolean x = new Boolean(true);  
            Integer e = new Integer(b); // warn
        }
     }
}
