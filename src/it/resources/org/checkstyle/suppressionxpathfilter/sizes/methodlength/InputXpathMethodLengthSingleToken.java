package org.checkstyle.suppressionxpathfilter.sizes.methodlength;

public class InputXpathMethodLengthSingleToken {

    public void methodOne() { }

    InputXpathMethodLengthSingleToken a =
            new InputXpathMethodLengthSingleToken(){
        public void methodOne(){ // warn

        }
    };
}
