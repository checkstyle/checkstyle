/*
OverloadMethodsDeclarationOrder


*/

package com.puppycrawl.tools.checkstyle.checks.coding.overloadmethodsdeclarationorder;

class InputOverloadMethodsDeclarationOrderConstructors {
    public InputOverloadMethodsDeclarationOrderConstructors(int a) {

    }

    public InputOverloadMethodsDeclarationOrderConstructors(int a, int b) {

    }

    public void testing() {

    }

    // violation: because overload never split
    public InputOverloadMethodsDeclarationOrderConstructors(String a) { // violation 'overloaded.constructors'

    }
}

