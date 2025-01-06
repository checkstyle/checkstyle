package org.checkstyle.suppressionxpathfilter.redundantmodifier;

public class InputXpathRedundantModifierNestedEnum {

    static enum Enum1 { //warn
        VALUE1, VALUE2
    }

    static enum Enum2 { //warn
        VALUE1, VALUE2
    }


}
