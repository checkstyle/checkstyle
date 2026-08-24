/*
LeftCurly
option = (default)eol
ignoreEnums = false
tokens = (default)ANNOTATION_DEF, CLASS_DEF, CTOR_DEF, ENUM_CONSTANT_DEF, ENUM_DEF, \
         INTERFACE_DEF, LAMBDA, LITERAL_CASE, LITERAL_CATCH, LITERAL_DEFAULT, \
         LITERAL_DO, LITERAL_ELSE, LITERAL_FINALLY, LITERAL_FOR, LITERAL_IF, \
         LITERAL_SWITCH, LITERAL_SYNCHRONIZED, LITERAL_TRY, LITERAL_WHILE, METHOD_DEF, \
         OBJBLOCK, STATIC_INIT, RECORD_DEF, COMPACT_CTOR_DEF, SWITCH_RULE


*/

package com.puppycrawl.tools.checkstyle.checks.blocks.leftcurly;

public class InputLeftCurlyTestTypesEol {}

// violation below ''{' at column 11 should have line break after'
class Foo { int a;
}

// violation below ''{' at column 15 should have line break after'
interface Bar { int b = 1;
}

// violation below ''{' at column 17 should have line break after'
record FooBar() { static int c;
}

// violation below ''{' at column 12 should have line break after'
enum  Bazz { VALUE,
    VALUE2
}

// violation below ''{' at column 17 should have line break after'
@interface Foo2 { int d = 1;
}

// violation below ''{' at column 12 should have line break after'
class Bar2 {{
    int a = 1;
}}
