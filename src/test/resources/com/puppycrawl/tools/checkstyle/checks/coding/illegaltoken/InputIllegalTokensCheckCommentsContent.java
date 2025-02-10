/* // violation, ''
IllegalToken
tokens = COMMENT_CONTENT


*/



/** // violation, ''
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
            anotherLabel: // some comment href // violation, ''
            do {
                continue anotherLabel;
            } while (false);
            break label; // some a href // violation, ''
        }
    }
}
