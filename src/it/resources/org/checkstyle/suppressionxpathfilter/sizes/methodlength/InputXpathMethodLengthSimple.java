/*
MethodLength
max = 10
countEmpty = (default)true
tokens = (default)METHOD_DEF, CTOR_DEF, COMPACT_CTOR_DEF


*/

package org.checkstyle.suppressionxpathfilter.sizes.methodlength;

public class InputXpathMethodLengthSimple {
    // violation below 'Method InputXpathMethodLengthSimple length is 11 lines (max allowed is 10).'
    protected InputXpathMethodLengthSimple() {
        // a line
        // a line
        // a line
        // a line
        // a line
        // a line
        // a line
        // a line

    }

    private void methodOne(){
        // a line
        // a line
        // a line
        // a line
        // a line
        // a line
        // a line
        // a line
    }

}
