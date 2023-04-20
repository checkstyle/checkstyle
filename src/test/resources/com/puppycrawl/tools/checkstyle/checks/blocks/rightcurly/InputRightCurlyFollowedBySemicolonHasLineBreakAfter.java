/*
RightCurly
option = ALONE_OR_SINGLELINE
tokens = LITERAL_TRY , LITERAL_CATCH , LITERAL_FINALLY , LITERAL_IF , LITERAL_ELSE , CLASS_DEF , \
         METHOD_DEF , CTOR_DEF , LITERAL_FOR , LITERAL_WHILE , LITERAL_DO , STATIC_INIT , \
         INSTANCE_INIT , ANNOTATION_DEF , ENUM_DEF , INTERFACE_DEF , RECORD_DEF , COMPACT_CTOR_DEF \
         , VARIABLE_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.blocks.rightcurly;

public class InputRightCurlyFollowedBySemicolonHasLineBreakAfter {


        Object obj2_normal = new Object();

        Object obj_anon = new Object() {
        }; int i = 0; // violation '';' at column 10 should have line break after'

        void method1() {
        } int j = 10; // violation ''}' at column 9 should be alone on a line'

        class SomeClass {
        } int k = 4; // violation ''}' at column 9 should be alone on a line'

}
