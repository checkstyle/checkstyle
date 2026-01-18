/*
GoogleNonConstantFieldName

*/
package com.puppycrawl.tools.checkstyle.checks.naming.googlenonconstantfieldname;

/** Test inner class non-constant field names are checked. */
public class InputGoogleNonConstantFieldNameInnerClasses {

    class InnerClass {

        int Inner_Bad;
        // violation above, 'Non-constant field name 'Inner_Bad' has invalid underscore usage'

        int innerValid;

        class NestedClass {

            int Nested_Bad;
            // violation above, 'Non-constant field name 'Nested_Bad' has invalid underscore usage'

            int nestedValid;
        }
    }

    static class StaticInner {
        int staticInnerField;
        int Static_Bad;
        // violation above, 'Non-constant field name 'Static_Bad' has invalid underscore usage'
    }
}
