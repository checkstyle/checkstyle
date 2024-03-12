package org.checkstyle.suppressionxpathfilter.genericwhitespace;

import java.io.Serializable;

public class InputXpathGenericWhitespaceNestedGenericsThree {
    <E extends Enum<E> , X> void bad() {//warn
    }
    <E extends Enum<E>, X> void good() {
    }
}
