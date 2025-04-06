/*
RedundantModifier
jdkVersion = (default)22
tokens = (default)METHOD_DEF, VARIABLE_DEF, ANNOTATION_FIELD_DEF, INTERFACE_DEF, \
         CTOR_DEF, CLASS_DEF, ENUM_DEF, RESOURCE


*/

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
    public PackagePrivateClass() {} // violation
}

class PackagePrivateClassWithNotRedundantConstructor {
    PackagePrivateClassWithNotRedundantConstructor() {}

}
