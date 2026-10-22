/*
EmptyForIteratorPad
option = (default)nospace


*/

package org.checkstyle.suppressionxpathfilter.whitespace.emptyforiteratorpad;

public class InputXpathEmptyForIteratorPadFollowedStaticInit {
    static {
        for (int i=0; i < 10; ){ // violation "';' is followed by whitespace."
        }
        for (int j=0; j < 10;){
        }
    }
}
