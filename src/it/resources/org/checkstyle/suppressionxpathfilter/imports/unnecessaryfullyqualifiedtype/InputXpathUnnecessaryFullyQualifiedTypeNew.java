package org.checkstyle.suppressionxpathfilter.imports.unnecessaryfullyqualifiedtype;

public class InputXpathUnnecessaryFullyQualifiedTypeNew {
    void method() {
        Object list = new java.util.ArrayList(); // warn
    }
}
