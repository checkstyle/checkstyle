package org.checkstyle.suppressionxpathfilter.whitespace.genericwhitespace;

import java.util.function.Consumer;

public class InputXpathGenericWhitespaceStartTwo {
    public <E> void bad(Consumer <E> consumer) {//warn
    }
    public <E> void good(Consumer<E> consumer) {
    }
}
