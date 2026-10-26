/*
GenericWhitespace


*/

package org.checkstyle.suppressionxpathfilter.whitespace.genericwhitespace;

import java.io.Serializable;

public class InputXpathGenericWhitespaceNestedTwo {
    <E extends Enum<E>  & Serializable> void bad() { // violation "'>' is followed by whitespace."
    }
    <E extends Enum<E> & Serializable> void good() {
    }
}
