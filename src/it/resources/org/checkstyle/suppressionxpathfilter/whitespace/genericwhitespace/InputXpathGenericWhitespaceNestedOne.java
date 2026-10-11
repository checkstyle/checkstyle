package org.checkstyle.suppressionxpathfilter.whitespace.genericwhitespace;

import java.io.Serializable;

public class InputXpathGenericWhitespaceNestedOne {
    <E extends Enum<E>& Serializable> void bad() {//warn
    }
    <E extends Enum<E> & Serializable> void good() {
    }
}
