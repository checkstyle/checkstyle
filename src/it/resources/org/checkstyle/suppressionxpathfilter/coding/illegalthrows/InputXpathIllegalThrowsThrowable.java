package org.checkstyle.suppressionxpathfilter.coding.illegalthrows;

public class InputXpathIllegalThrowsThrowable {

    Object obj=new Object() {
        public void test() throws Throwable //warn
        {
        }
    };
}
