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

        Object obj_anon = new Object() {};

        Object obj_anon = new Object() {

        public void method_in_anon_class() {};

        };

        Object obj_anon = new Object() {

        class inner_class{};

        };

        Object obj_anon2 = new Object() {

        Object obj_inner_anon2 = new Object() {

        };

        };


}
