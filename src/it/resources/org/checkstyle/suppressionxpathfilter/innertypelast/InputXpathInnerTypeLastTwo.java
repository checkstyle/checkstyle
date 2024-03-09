package org.checkstyle.suppressionxpathfilter.innertypelast;

public class InputXpathInnerTypeLastTwo {

    static {}; // OK

    public void innerMethod() {} // OK

    class Inner {
        class InnerInInner {}
        public void innerMethod() {}  // warn

    };

}
