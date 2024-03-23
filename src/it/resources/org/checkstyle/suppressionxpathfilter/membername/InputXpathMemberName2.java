package org.checkstyle.suppressionxpathfilter.membername;

public class InputXpathMemberName2 {

    class Inner {
        public int NUM1; // warn
        protected int num2; // OK
    }

}
