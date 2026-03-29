package org.checkstyle.suppressionxpathfilter.coding.multiplevariabledeclarations;

public class InputXpathMultipleVariableDeclarationsInMethod {
    void foo() {
        int a, b; //warn
    }
}
