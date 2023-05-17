/*
RightCurly
option = SAME
tokens = LITERAL_TRY , LITERAL_CATCH , LITERAL_FINALLY , LITERAL_IF , LITERAL_ELSE , CLASS_DEF , \
         METHOD_DEF , CTOR_DEF , LITERAL_FOR , LITERAL_WHILE , LITERAL_DO , STATIC_INIT , \
         INSTANCE_INIT , ANNOTATION_DEF , ENUM_DEF , INTERFACE_DEF , RECORD_DEF , COMPACT_CTOR_DEF \
         , OBJBLOCK


*/


package com.puppycrawl.tools.checkstyle.checks.blocks.rightcurly;

public class InputRightCurlyBracePolicy {
        Object obj_anon = new Object() {
        }; int i = 0; // ok
}
