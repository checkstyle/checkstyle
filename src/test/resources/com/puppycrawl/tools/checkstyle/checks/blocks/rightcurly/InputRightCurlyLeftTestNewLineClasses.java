/*
RightCurly
option = ALONE
tokens = CLASS_DEF, METHOD_DEF, CTOR_DEF, ANNOTATION_DEF, ENUM_DEF, INTERFACE_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.blocks.rightcurly;

/**
 * Test input for closing brace if that brace terminates
 * a statement or the body of a constructor.
 */
class InputRightCurlyLeftTestNewLineClasses
{
        int i;
        public void FooCtor()
    {
                i = 1;
    }}
// 2 violations above:
// ''}' at column 5 should be alone on a line.'
// ''}' at column 6 should be alone on a line.'

/**
* Test input for closing brace if that brace terminates
* a statement or the body of a method.
*/
class FooMethodTestNewLine
{
        public void fooMethod()
    {
                int i = 1;
    }}
// 2 violations above:
// ''}' at column 5 should be alone on a line.'
// ''}' at column 6 should be alone on a line.'

/**
* Test input for closing brace if that brace terminates
* a statement or the body of a named class.
*/
class FooInnerTestNewLine
{
        class InnerFoo
    {
                public void fooInnerMethod ()
        {

                }
    }}
// 2 violations above:
// ''}' at column 5 should be alone on a line.'
// ''}' at column 6 should be alone on a line.'

/**
 * False positive
 *
 */
class Absent_CustomFieldSerializer3TestNewLine {

    public static void serialize() {} // violation ''}' at column 37 should be alone on a line'
}

class Absent_CustomFieldSerializer4TestNewLine
{
  void Absent_CustomFieldSerializer4() {} // violation ''}' at column 41 should be alone on a line'
}

class EmptyClass2TestNewLine {} // violation ''}' at column 31 should be alone on a line'

interface EmptyInterface3TestNewLine {} // violation ''}' at column 39 should be alone on a line'
