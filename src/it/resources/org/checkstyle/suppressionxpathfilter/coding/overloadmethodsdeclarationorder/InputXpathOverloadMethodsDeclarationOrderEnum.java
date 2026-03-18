package org.checkstyle.suppressionxpathfilter.coding.overloadmethodsdeclarationorder;

public enum InputXpathOverloadMethodsDeclarationOrderEnum {

    VALUE;

    public void overloadMethod(String s) {
        //do stuff
    }

    public void separatorMethod() {
        //do stuff
    }

    public void overloadMethod(String s, Boolean b, int i) { // warn
        //do stuff
    }
}
