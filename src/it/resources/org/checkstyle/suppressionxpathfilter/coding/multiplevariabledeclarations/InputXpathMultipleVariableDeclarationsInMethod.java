/*
MultipleVariableDeclarations


*/

package org.checkstyle.suppressionxpathfilter.coding.multiplevariabledeclarations;

public class InputXpathMultipleVariableDeclarationsInMethod {
    void foo() {
        int a, b; // violation 'Each variable declaration must be in its own statement.'
    }
}
