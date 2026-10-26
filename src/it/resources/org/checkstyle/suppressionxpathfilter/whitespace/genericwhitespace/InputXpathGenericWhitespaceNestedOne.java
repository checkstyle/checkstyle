/*
GenericWhitespace


*/

package org.checkstyle.suppressionxpathfilter.whitespace.genericwhitespace;

import java.io.Serializable;

public class InputXpathGenericWhitespaceNestedOne {
    // violation below "'&' is not preceded with whitespace."
    <E extends Enum<E>& Serializable> void bad() {
    }
    <E extends Enum<E> & Serializable> void good() {
    }
}
