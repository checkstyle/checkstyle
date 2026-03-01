/*
LeftCurly
option = (default)eol
ignoreEnums = (default)true
tokens = (default)ANNOTATION_DEF, CLASS_DEF, CTOR_DEF, ENUM_CONSTANT_DEF, \
         ENUM_DEF, INTERFACE_DEF, LAMBDA, LITERAL_CASE, LITERAL_CATCH, \
         LITERAL_DEFAULT, LITERAL_DO, LITERAL_ELSE, LITERAL_FINALLY, LITERAL_FOR, \
         LITERAL_IF, LITERAL_SWITCH, LITERAL_SYNCHRONIZED, LITERAL_TRY, LITERAL_WHILE, \
         METHOD_DEF, OBJBLOCK, STATIC_INIT, RECORD_DEF, COMPACT_CTOR_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.blocks.leftcurly;

class InputLeftCurlyTestDefault3
{ // violation ''{' at column 1 should be on the previous line'
}

/**
 * Test input for closing brace if that brace terminates
 * a statement or the body of a constructor.
 */
class FooCtor
{ // violation ''{' at column 1 should be on the previous line'
        int i;
        public FooCtor()
    { // violation ''{' at column 5 should be on the previous line'
                i = 1;
    }}

/**
* Test input for closing brace if that brace terminates
* a statement or the body of a method.
*/
class FooMethod
{ // violation ''{' at column 1 should be on the previous line'
        public void fooMethod()
    { // violation ''{' at column 5 should be on the previous line'
                int i = 1;
    }}

/**
* Test input for closing brace if that brace terminates
* a statement or the body of a named class.
*/
class FooInner
{ // violation ''{' at column 1 should be on the previous line'
        class InnerFoo
    { // violation ''{' at column 5 should be on the previous line'
                public void fooInnerMethod ()
        { // violation ''{' at column 9 should be on the previous line'

                }
    }}
