package org.checkstyle.suppressionxpathfilter.genericwhitespace;

import java.io.Serializable;

public class InputXpathGenericWhitespaceSingleGenericTwo {
    <E>void bad() {//warn
    }
    <E> void good() {
    }
}
