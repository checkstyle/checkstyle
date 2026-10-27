/*
GenericWhitespace


*/

package org.checkstyle.suppressionxpathfilter.whitespace.genericwhitespace;

import java.io.Serializable;

public class InputXpathGenericWhitespaceSingleTwo {
    <E>void bad() { // violation "'>' is followed by an illegal character."
    }
    <E> void good() {
    }
}
