/*
RedundantModifier
jdkVersion = (default)22
tokens = (default)METHOD_DEF, VARIABLE_DEF, ANNOTATION_FIELD_DEF, INTERFACE_DEF, \
         CTOR_DEF, CLASS_DEF, ENUM_DEF, RESOURCE, ANNOTATION_DEF, RECORD_DEF, \
         PATTERN_VARIABLE_DEF, LITERAL_CATCH, LAMBDA


*/

package com.puppycrawl.tools.checkstyle.checks.modifier.redundantmodifier;

public class InputRedundantModifierPublicConstructorsInNestedClasses {
    private static class PrivateNested {
        public PrivateNested() {}
    }

    static class PackageNested {
        public PackageNested() {}
    }

    protected static class ProtectedNested {
        public ProtectedNested() {}
    }

    private static class DeeplyNested {
        public static class PublicNested {
            public PublicNested() {}
        }
    }
}

class PackagePrivateOuter {
    public static class PublicNested {
        public PublicNested() {}
    }

    public PackagePrivateOuter() {} // violation 'Redundant 'public' modifier.'
}
