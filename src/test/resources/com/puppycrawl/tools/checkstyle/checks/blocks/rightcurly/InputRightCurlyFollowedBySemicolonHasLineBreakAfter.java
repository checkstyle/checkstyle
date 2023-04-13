/*
RightCurly
option = ALONE_OR_SINGLELINE
tokens = LITERAL_TRY , LITERAL_CATCH , LITERAL_FINALLY , LITERAL_IF , LITERAL_ELSE , CLASS_DEF , METHOD_DEF , CTOR_DEF , LITERAL_FOR , LITERAL_WHILE , LITERAL_DO , STATIC_INIT , INSTANCE_INIT , ANNOTATION_DEF , ENUM_DEF , INTERFACE_DEF , RECORD_DEF , COMPACT_CTOR_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.blocks.rightcurly;

public class InputRightCurlyFollowedBySemicolonHasLineBreakAfter {

        Object o = new Object() {
        }; int i = 0; // violation '';' at column 10 should have line break after'

        class Inner {
        } int x = 0; // violation ''}' at column 9 should be alone on a line'

        void m() {
        } int y = 0; // violation ''}' at column 9 should be alone on a line'

}
