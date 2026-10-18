/*
IllegalToken
tokens = POST_INC


*/

package org.checkstyle.suppressionxpathfilter.coding.illegaltoken;

public enum InputXpathIllegalTokenPostIncrement {
    VALUE;

    void method() {
        int i = 0;
        i++; // violation 'Using '++' is not allowed.'
    }
}
