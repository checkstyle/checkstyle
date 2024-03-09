package org.checkstyle.suppressionxpathfilter.genericwhitespace;

import java.io.Serializable;

public class InputXpathGenericWhitespaceNestedGenericsOne {
    <E extends Enum<E>& Serializable> void bad() {//warn
    }
    <E extends Enum<E> & Serializable> void good() {
    }
}
