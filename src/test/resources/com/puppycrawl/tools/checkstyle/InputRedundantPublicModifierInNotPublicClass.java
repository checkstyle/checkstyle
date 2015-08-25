////////////////////////////////////////////////////////////////////////////////
// Test case file for checkstyle.
// Created: 2015
////////////////////////////////////////////////////////////////////////////////
package com.puppycrawl.tools.checkstyle;

public class InputRedundantPublicModifierInNotPublicClass {
    public InputRedundantPublicModifierInNotPublicClass() { }
    protected class ProtectedClass {
        public ProtectedClass() {}
    }
}

class PackagePrivateClass {
    public PackagePrivateClass() {} // violation expected
}

class PackagePrivateClassWithNotRedundantConstructor {
    PackagePrivateClassWithNotRedundantConstructor() {}
}
