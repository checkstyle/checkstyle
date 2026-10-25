/*
OverloadMethodsDeclarationOrder
orderByIncreasingParameterCount = (default)false


*/

package org.checkstyle.suppressionxpathfilter.coding.overloadmethodsdeclarationorder;

public class InputXpathOverloadMethodsDeclarationOrderDefault {

    public void overloadMethod(String s) {
        //do stuff
    }

    public void separatorMethod() {
        //do stuff
    }

    // violation 2 lines below """All overloaded methods should be placed next to each
    // other. Previous overloaded method located at line '12'."""
    public void overloadMethod(String s, Boolean b, int i) {
        //do stuff
    }

}
