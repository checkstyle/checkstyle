package org.checkstyle.suppressionxpathfilter.innertypelast;

public class InputXpathInnerTypeLastThree {

    static {} // OK

    interface Inner {
    }

    public InputXpathInnerTypeLastThree() { // warn
    }

}
