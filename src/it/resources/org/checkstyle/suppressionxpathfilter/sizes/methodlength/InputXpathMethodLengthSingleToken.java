/*
MethodLength
max = 1
countEmpty = (default)true
tokens = METHOD_DEF


*/

package org.checkstyle.suppressionxpathfilter.sizes.methodlength;

public class InputXpathMethodLengthSingleToken {

    public void methodOne() { }

    InputXpathMethodLengthSingleToken a =
            new InputXpathMethodLengthSingleToken(){
        // violation below 'Method methodOne length is 3 lines (max allowed is 1).'
        public void methodOne(){

        }
    };
}
