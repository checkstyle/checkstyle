/*
InnerTypeLast


*/

package org.checkstyle.suppressionxpathfilter.design.innertypelast;

public class InputXpathInnerTypeLastTwo {

    static {};

    public void innerMethod() {}

    class Inner {
        class InnerInInner {}
        // violation 2 lines below """Init blocks, constructors, fields and methods
        //     should be before inner types."""
        public void innerMethod() {}

    };

}
