package org.checkstyle.suppressionxpathfilter.overloadmethodsdeclarationorder;

public class InputXpathOverloadMethodsDeclarationOrder2 {
    private InputXpathOverloadMethodsDeclarationOrder2 anonymous
            = new MyInputXpathOverloadMethodsDeclarationOrder2();

    public InputXpathOverloadMethodsDeclarationOrder2 getAnonymous() {
        return anonymous;
    }

    public void setAnonymous(InputXpathOverloadMethodsDeclarationOrder2 anonymous) {
        this.anonymous = anonymous;
    }

    private static class MyInputXpathOverloadMethodsDeclarationOrder2
            extends InputXpathOverloadMethodsDeclarationOrder2 {
        public void overloadMethod(int i) {
            //do stuff
        }

        public void overloadMethod(String s) {
            //do more stuff
        }

        public void separatorMethod() {
            //do other stuff
        }

        //violation because overloads shouldn't be separated
        public void overloadMethod(String s, Boolean b, int i) { //warn
            //do stuff
        }
    }
}
