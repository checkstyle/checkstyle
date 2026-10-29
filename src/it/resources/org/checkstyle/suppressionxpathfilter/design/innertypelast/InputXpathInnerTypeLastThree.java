/*
InnerTypeLast


*/

package org.checkstyle.suppressionxpathfilter.design.innertypelast;

public class InputXpathInnerTypeLastThree {

    static {}

    interface Inner {
    }

    // violation 2 lines below """Init blocks, constructors, fields and methods
    //     should be before inner types."""
    public InputXpathInnerTypeLastThree() {
    }

}
