package com.puppycrawl.tools.checkstyle.checks.naming.illegalidentifiername;

/* Config:
 *
 * default
 */
public class InputIllegalIdentifierNameUnderscore {
    String _string = "_string"; // ok
    String string_ = "string_"; // ok
    String another_string = "string"; // ok
    String _; // violation
}
