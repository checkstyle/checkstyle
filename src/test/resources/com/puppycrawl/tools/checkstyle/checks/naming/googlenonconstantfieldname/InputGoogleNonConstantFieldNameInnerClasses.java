/*
GoogleNonConstantFieldName

*/
package com.puppycrawl.tools.checkstyle.checks.naming.googlenonconstantfieldname;

/** Test inner class non-constant field names are checked. */
public class InputGoogleNonConstantFieldNameInnerClasses {

    class InnerClass {

        int Inner_Bad;
        // violation above, ''Inner_Bad' .* underscores allowed only between adjacent digits.'

        int innerValid;

        class NestedClass {

            int Nested_Bad;
            // violation above, ''Nested_Bad' .* underscores allowed only between adjacent digits.'

            int nestedValid;
        }
    }

    static class StaticInner {
        int staticInnerField;
        int Static_Bad;
        // violation above, ''Static_Bad' .* underscores allowed only between adjacent digits.'
    }
}
