package org.checkstyle.suppressionxpathfilter.overloadmethodsdeclarationorder;

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

        //violation because overloads shouldn't be separated
        public void overloadMethod(String s, Boolean b, int i) { //warn
            //do stuff
        }
    }
}
