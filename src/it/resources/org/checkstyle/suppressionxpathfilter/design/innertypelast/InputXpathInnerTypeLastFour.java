/*
InnerTypeLast


*/

package org.checkstyle.suppressionxpathfilter.design.innertypelast;

public class InputXpathInnerTypeLastFour {

    enum Status {
        ACTIVE, INACTIVE
    }

    // violation 2 lines below """Init blocks, constructors, fields and methods
    //     should be before inner types."""
    private int count;

}

