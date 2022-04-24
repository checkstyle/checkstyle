package org.checkstyle.suppressionxpathfilter.overloadmethodsdeclarationorder;

public class SuppressionXpathRegressionOverloadMethodsDeclarationOrder2 {
    private SuppressionXpathRegressionOverloadMethodsDeclarationOrder2 anonymous
            = new MySuppressionXpathRegressionOverloadMethodsDeclarationOrder2();

    public SuppressionXpathRegressionOverloadMethodsDeclarationOrder2 getAnonymous() {
        return anonymous;
    }

    public void setAnonymous(SuppressionXpathRegressionOverloadMethodsDeclarationOrder2 anonymous) {
        this.anonymous = anonymous;
    }

    private static class MySuppressionXpathRegressionOverloadMethodsDeclarationOrder2
            extends SuppressionXpathRegressionOverloadMethodsDeclarationOrder2 {
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
