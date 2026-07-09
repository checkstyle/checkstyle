/*
RedundantModifier
jdkVersion = (default)22
tokens = (default)METHOD_DEF, VARIABLE_DEF, ANNOTATION_FIELD_DEF, INTERFACE_DEF, \
         CTOR_DEF, CLASS_DEF, ENUM_DEF, RESOURCE, ANNOTATION_DEF, RECORD_DEF, \
         PATTERN_VARIABLE_DEF, LITERAL_CATCH, LAMBDA


*/

package com.puppycrawl.tools.checkstyle.checks.modifier.redundantmodifier;

public class InputRedundantModifierPublicModifierInNotPublicClass {
    public InputRedundantModifierPublicModifierInNotPublicClass() { }
    protected class ProtectedClass {
        public ProtectedClass() {}
    }
    public class PublicInnerClass {
        public PublicInnerClass() { } // ok for class accessible from public scope
    }
}

class PackagePrivateClass {
    public PackagePrivateClass() {} // violation 'Redundant 'public' modifier.'
}

class PackagePrivateClassWithNotRedundantConstructor {
    PackagePrivateClassWithNotRedundantConstructor() {}

}
