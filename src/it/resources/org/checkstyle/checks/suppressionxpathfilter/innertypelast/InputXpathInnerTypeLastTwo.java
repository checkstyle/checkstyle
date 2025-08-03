package org.checkstyle.checks.suppressionxpathfilter.innertypelast;

public class InputXpathInnerTypeLastTwo {

    static {}; // OK

    public void innerMethod() {} // OK

    class Inner {
        class InnerInInner {}
        public void innerMethod() {}  // warn

    };

}
