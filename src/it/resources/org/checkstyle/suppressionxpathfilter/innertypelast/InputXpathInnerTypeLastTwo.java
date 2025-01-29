package org.checkstyle.suppressionxpathfilter.innertypelast;

public class InputXpathInnerTypeLastTwo {

    static {};  

    public void innerMethod() {}  

    class Inner {
        class InnerInInner {}
        public void innerMethod() {}  // warn

    };

}
