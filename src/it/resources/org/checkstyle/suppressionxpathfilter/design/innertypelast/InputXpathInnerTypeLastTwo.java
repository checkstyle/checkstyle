package org.checkstyle.suppressionxpathfilter.design.innertypelast;

public class InputXpathInnerTypeLastTwo {

    static {};

    public void innerMethod() {}

    class Inner {
        class InnerInInner {}
        public void innerMethod() {}  // warn

    };

}
