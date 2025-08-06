package org.checkstyle.suppressionxpathfilter.design.innertypelast;

public class InputXpathInnerTypeLastThree {

    static {} // OK

    interface Inner {
    }

    public InputXpathInnerTypeLastThree() { // warn
    }

}
