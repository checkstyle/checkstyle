package org.checkstyle.suppressionxpathfilter.whitespace.genericwhitespace;

import java.util.Collections;

public class InputXpathGenericWhitespaceSingleOne {
    Object bad = Collections.<Object> emptySet();//warn
    Object good = Collections.<Object>emptySet();
}
