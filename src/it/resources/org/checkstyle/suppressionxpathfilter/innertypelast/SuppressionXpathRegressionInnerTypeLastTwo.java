package org.checkstyle.suppressionxpathfilter.innertypelast;

public class SuppressionXpathRegressionInnerTypeLastTwo {

    static {}; // OK

    public void innerMethod() {} // OK

    class Inner {
        class InnerInInner {}
        public void innerMethod() {}  // warn

    };

}
