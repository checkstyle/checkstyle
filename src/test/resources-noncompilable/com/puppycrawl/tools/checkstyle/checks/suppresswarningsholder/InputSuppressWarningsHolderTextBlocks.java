//non-compiled with javac: Compilable with Java14
package com.puppycrawl.tools.checkstyle.checks.suppresswarningsholder;

/* Config:
 * aliasList = null
 */
public class InputSuppressWarningsHolderTextBlocks {
    @SuppressWarnings({"membername"})
    String STRING1 = "string"; //ok, suppressed

    @SuppressWarnings({"membername"})
    String STRING2 = """
            string"""; // ok, suppressed

    String STRING3 = "string"; // violation

    String STRING4 = """
            string"""; // violation

    @SuppressWarnings({"""
            membername"""})
    String STRING5 = """
            string"""; // ok, suppressed

    @SuppressWarnings({
        """
            membername
        """
    })
    String STRING6 = """
        string"""; // ok, suppressed

    @SuppressWarnings({
        """
            checkstyle:membername
        """
    })
    String STRING7 = """
        string"""; // ok, suppressed

    @SuppressWarnings({
        """
                checkstyle:misspelled
        """
    })
    String STRING8 = """
        string"""; // violation
}
