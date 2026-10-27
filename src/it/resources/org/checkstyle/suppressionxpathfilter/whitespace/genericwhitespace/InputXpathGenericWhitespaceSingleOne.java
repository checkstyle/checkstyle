/*
GenericWhitespace


*/

package org.checkstyle.suppressionxpathfilter.whitespace.genericwhitespace;

import java.util.Collections;

public class InputXpathGenericWhitespaceSingleOne {
    Object bad = Collections.<Object> emptySet(); // violation "'>' is followed by whitespace."
    Object good = Collections.<Object>emptySet();
}
