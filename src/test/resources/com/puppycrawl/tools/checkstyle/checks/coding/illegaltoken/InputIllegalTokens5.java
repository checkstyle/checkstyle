/* // violation
IllegalToken
tokens = BLOCK_COMMENT_BEGIN


*/

package com.puppycrawl.tools.checkstyle.checks.coding.illegaltoken;

/** // violation
 * Test for illegal tokens
 */
public class InputIllegalTokens5
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
            anotherLabel: // some comment href
            do {
                continue anotherLabel;
            } while (false);
            break label; // some a href
        }
    }
}
