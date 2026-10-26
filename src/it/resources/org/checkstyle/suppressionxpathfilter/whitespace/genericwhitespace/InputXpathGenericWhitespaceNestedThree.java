/*
GenericWhitespace


*/

package org.checkstyle.suppressionxpathfilter.whitespace.genericwhitespace;

import java.io.Serializable;

public class InputXpathGenericWhitespaceNestedThree {
    <E extends Enum<E> , X> void bad() { // violation "'>' is followed by whitespace."
    }
    <E extends Enum<E>, X> void good() {
    }
}
