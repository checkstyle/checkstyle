package org.checkstyle.suppressionxpathfilter.whitespace.emptyforiteratorpad;

public class InputXpathEmptyForIteratorPadFollowedStaticInit {
    static {
        for (int i=0; i < 10; ){ //warn
        }
        for (int j=0; j < 10;){
        }
    }
}
