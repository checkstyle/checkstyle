/*
com.puppycrawl.tools.checkstyle.checks.naming.MemberNameCheck
format = (default)^[a-z][a-zA-Z0-9]*$
applyToPublic = (default)true
applyToProtected = (default)true
applyToPackage = (default)true
applyToPrivate = (default)true


*/

package com.puppycrawl.tools.checkstyle.grammar;
/**
 * Input for grammar test.
 */
public class InputGrammar
{
    int ÃЯ = 1; // illegal, unless UTF-8 // violation 'Name 'ÃЯ' must match pattern'
}
