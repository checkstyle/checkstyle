/*
UnnecessarySemicolonAfterTypeMemberDeclaration
tokens = (default)CLASS_DEF, INTERFACE_DEF, ENUM_DEF, ANNOTATION_DEF, VARIABLE_DEF, \
         ANNOTATION_FIELD_DEF, STATIC_INIT, INSTANCE_INIT, CTOR_DEF, METHOD_DEF, \
         ENUM_CONSTANT_DEF, COMPACT_CTOR_DEF, RECORD_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.coding.unnecessarysemicolonaftertypememberdeclaration;

public class InputUnnecessarySemicolonAfterTypeMemberDeclarationNullAst {
    public static void main(String[] args) { // ok
        switch (args.length) { // ok
            case 0: // ok
                final int k = 12; // ok
                class Local { // ok
                    int j = k; // ok
                }
            case 1: // ok
        }
    }
}
