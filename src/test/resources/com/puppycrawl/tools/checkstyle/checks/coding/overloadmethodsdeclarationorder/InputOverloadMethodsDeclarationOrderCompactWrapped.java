/*
OverloadMethodsDeclarationOrder

*/
package com.puppycrawl.tools.checkstyle.checks.coding.overloadmethodsdeclarationorder;

public class InputOverloadMethodsDeclarationOrderCompactWrapped {

    void process(int x) { }

    void helper() { }

    // violation below 'All overloaded methods should be placed next to each other.'
    void process(String s) { }

    void main() { }

}
