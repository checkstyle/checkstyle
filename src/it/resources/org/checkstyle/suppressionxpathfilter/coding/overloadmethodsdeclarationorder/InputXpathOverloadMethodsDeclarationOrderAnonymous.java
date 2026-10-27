/*
OverloadMethodsDeclarationOrder
orderByIncreasingParameterCount = (default)false


*/

package org.checkstyle.suppressionxpathfilter.coding.overloadmethodsdeclarationorder;

public class InputXpathOverloadMethodsDeclarationOrderAnonymous {
    private InputXpathOverloadMethodsDeclarationOrderAnonymous anonymous
            = new MyInputXpathOverloadMethodsDeclarationOrderAnonymous();

    public InputXpathOverloadMethodsDeclarationOrderAnonymous getAnonymous() {
        return anonymous;
    }

    public void setAnonymous(InputXpathOverloadMethodsDeclarationOrderAnonymous anonymous) {
        this.anonymous = anonymous;
    }

    private static class MyInputXpathOverloadMethodsDeclarationOrderAnonymous
            extends InputXpathOverloadMethodsDeclarationOrderAnonymous {
        public void overloadMethod(int i) {
            //do stuff
        }

        public void overloadMethod(String s) {
            //do more stuff
        }

        public void separatorMethod() {
            //do other stuff
        }

        // violation 2 lines below """All overloaded methods should be placed next to each
        // other. Previous overloaded method located at line '28'."""
        public void overloadMethod(String s, Boolean b, int i) {
            //do stuff
        }
    }
}
