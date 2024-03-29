package org.checkstyle.suppressionxpathfilter.genericwhitespace;

import java.io.Serializable;

public class InputXpathGenericWhitespaceSingleTwo {
    <E>void bad() {//warn
    }
    <E> void good() {
    }
}
