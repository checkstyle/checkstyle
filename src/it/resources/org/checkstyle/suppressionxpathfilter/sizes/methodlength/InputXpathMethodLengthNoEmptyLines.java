/*
MethodLength
max = 5
countEmpty = false
tokens = METHOD_DEF


*/

package org.checkstyle.suppressionxpathfilter.sizes.methodlength;

public class InputXpathMethodLengthNoEmptyLines {

    public InputXpathMethodLengthNoEmptyLines(){
        // a line
        // a line
        // a line
        // a line
        // a line


    }

    // violation below 'Method methodOne length is 6 lines (max allowed is 5).'
    protected void methodOne(){
        if(true){
            if(true){
            }
        }
    }

}
