public class InputTrailingComment {
    int i; // don't use trailing comments :)
    // it fine to have comment w/o any statement
    /* good c-style comment. */
    int j; /* bad c-style comment. */
    void method1() { /* some c-style multi-line
                        comment*/
        Runnable r = (new Runnable() {
                public void run() {
                }
            }); /* we should allow this */
    } // we should allow this
    /*
      Let's check multi-line comments.
    */
    /* c-style */ // we do not allow several comments on one line
    /* c-style 1 */ /*c-style 2 */
}
