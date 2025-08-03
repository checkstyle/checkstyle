package org.checkstyle.checks.suppressionxpathfilter.innertypelast;

public class InputXpathInnerTypeLastThree {

    static {} // OK

    interface Inner {
    }

    public InputXpathInnerTypeLastThree() { // warn
    }

}
