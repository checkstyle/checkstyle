////////////////////////////////////////////////////////////////////////////////
// Test case file for checkstyle.
// Created: 2015
////////////////////////////////////////////////////////////////////////////////
package com.puppycrawl.tools.checkstyle.checks.modifier.redundantmodifier;

public class InputRedundantModifierPublicModifierInNotPublicClass {
    public InputRedundantModifierPublicModifierInNotPublicClass() { }
    protected class ProtectedClass {
        public ProtectedClass() {}
    }
    public class PublicInnerClass {
        public PublicInnerClass() { } // OK for class accessible from public scope
    }
}

class PackagePrivateClass {
    public PackagePrivateClass() {} // violation expected
}

class PackagePrivateClassWithNotRedundantConstructor {
    PackagePrivateClassWithNotRedundantConstructor() {}

}
