/* // violation
IllegalToken
tokens = COMMENT_CONTENT


*/
// violation, 7 lines above 'Using ' \/\/ violation\\nIllegalToken\\ntokens = COMMENT_CONTENT\\n\\n\\n' is not allowed'
package com.puppycrawl.tools.checkstyle.checks.coding.illegaltoken;

/** //violation, ''
 * Test for illegal tokens
 */
// violation, 2 lines above 'Using '* \/\/ violation\\n * Test for illegal tokens\\n ' is not allowed'
public class InputIllegalTokensCheckCommentsContent
{
    public void methodWithPreviouslyIllegalTokens()
    {
        int i = 0;
        switch (i)
        {
            default:
                i--;
                i++;
                break;
        }
    }
    public native void nativeMethod();

    public void methodWithLiterals()
    {
        final String ref = "<a href=\"";
        final String refCase = "<A hReF=\"";
    }
    public void methodWithLabels() {
        label:
        {
            anotherLabel: // some comment href // violation, ''
            do {
                continue anotherLabel;
            } while (false);
            break label; // some a href // violation, ''
        }
        // violation, 6 lines above 'Using ' some comment href \/\/ violation \\n' is not allowed'
        // violation, 3 lines above 'Using 'some a href \/\/ violation\n' is not allowed'
    }
}