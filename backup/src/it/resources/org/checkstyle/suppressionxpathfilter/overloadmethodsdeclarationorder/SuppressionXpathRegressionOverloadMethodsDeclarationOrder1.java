package org.checkstyle.suppressionxpathfilter.overloadmethodsdeclarationorder;

public class SuppressionXpathRegressionOverloadMethodsDeclarationOrder1 {

    public void overloadMethod(String s) {
        //do stuff
    }

    public void separatorMethod() {
        //do stuff
    }

    //violation because overloads shouldn't be separated
    public void overloadMethod(String s, Boolean b, int i) { //warn
        //do stuff
    }

}
