/* // violation, 'Using ' \/\/ violation\\nIllegalToken\\ntokens = COMMENT_CONTENT\\n\\n\\n' is not allowed'
IllegalToken
tokens = BLOCK_COMMENT_BEGIN


*/

package com.puppycrawl.tools.checkstyle.checks.coding.illegaltoken;

/** violation, 'Using '* \/\/ violation\\n * Test for illegal tokens\\n ' is not allowed'
 * Test for illegal tokens
 */

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
            anotherLabel: /** violation, 'Using ' some comment href // violation \\n' is not allowed' */
            do {
                continue anotherLabel;
            } while (false);
            break label; /** violation, 'Using 'some a href \/\/ violation' is not allowed' */
        }
    }
}
