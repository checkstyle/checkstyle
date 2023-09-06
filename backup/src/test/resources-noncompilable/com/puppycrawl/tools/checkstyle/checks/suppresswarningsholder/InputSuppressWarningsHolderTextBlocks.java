/*
com.puppycrawl.tools.checkstyle.checks.SuppressWarningsHolder
aliasList = (default)

com.puppycrawl.tools.checkstyle.filters.SuppressWarningsFilter

com.puppycrawl.tools.checkstyle.checks.naming.MemberNameCheck
format = (default)^[a-z][a-zA-Z0-9]*$
applyToPublic = (default)true
applyToProtected = (default)true
applyToPackage = (default)true
applyToPrivate = (default)true


*/

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

    String STRING3 = "string"; // violation ''STRING3' must match pattern'

    String STRING4 = "string"; // violation ''STRING4' must match pattern'

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
    String STRING8 = "string"; // violation ''STRING8' must match pattern'
}
