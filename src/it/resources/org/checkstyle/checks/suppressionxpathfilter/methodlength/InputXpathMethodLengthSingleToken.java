package org.checkstyle.checks.suppressionxpathfilter.methodlength;

public class InputXpathMethodLengthSingleToken {

    public void methodOne() { }

    InputXpathMethodLengthSingleToken a =
            new InputXpathMethodLengthSingleToken(){
        public void methodOne(){ // warn

        }
    };
}
