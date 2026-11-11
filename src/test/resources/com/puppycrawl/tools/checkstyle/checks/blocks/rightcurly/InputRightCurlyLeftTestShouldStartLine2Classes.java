/*
RightCurly
option = ALONE_OR_SINGLELINE
tokens = CLASS_DEF, METHOD_DEF, ANNOTATION_DEF, ENUM_DEF, INTERFACE_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.blocks.rightcurly;

/**
 * Test input for closing brace if that brace terminates
 * a statement or the body of a constructor.
 */
class InputRightCurlyLeftTestShouldStartLine2Classes
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
class FooMethodTestShouldStartLine2
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
class FooInnerTestShouldStartLine2
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
class Absent_CustomFieldSerializer3TestShouldStartLine2 {

    public static void serialize() {}
}

class Absent_CustomFieldSerializer4TestShouldStartLine2
{
  void Absent_CustomFieldSerializer4() {}
}

class EmptyClass2TestShouldStartLine2 {}

interface EmptyInterface3TestShouldStartLine2 {}
