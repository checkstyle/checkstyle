/*
UnnecessarySemicolonAfterTypeMemberDeclaration
tokens = (default)CLASS_DEF, INTERFACE_DEF, ENUM_DEF, ANNOTATION_DEF, VARIABLE_DEF, \
         ANNOTATION_FIELD_DEF, STATIC_INIT, INSTANCE_INIT, CTOR_DEF, METHOD_DEF, \
         ENUM_CONSTANT_DEF, COMPACT_CTOR_DEF, RECORD_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.coding.unnecessarysemicolonaftertypememberdeclaration;

public class InputUnnecessarySemicolonAfterTypeMemberDeclarationNullAst {
    public static void main(String[] args) {
        switch (args.length) {
            case 0:
                final int k = 12;
                class Local1 {
                    int j = k;
                }
                class Local2 {
                    int j = k;
                };
                class Local3 {
                    int l = 15;; //violation
                }
            case 1:
        }
    }
}
