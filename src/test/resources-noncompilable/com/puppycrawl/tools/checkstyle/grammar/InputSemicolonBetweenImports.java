/*
com.puppycrawl.tools.checkstyle.checks.naming.MemberNameCheck
format = (default)^[a-z][a-zA-Z0-9]*$
applyToPublic = (default)true
applyToProtected = (default)true
applyToPackage = (default)true
applyToPrivate = (default)true


*/

// non-compiled with eclipse: syntax error but works fine in jdk
// non-compiled with javac: but was compiled on jdk before 21, so we need to continue to support
package com.puppycrawl.tools.checkstyle.grammar;
; // non-compilable by eclipse
import java.util.ArrayList;
/**
 * Compilable by javac, but noncompilable by eclipse due to
 * this <a href="https://bugs.eclipse.org/bugs/show_bug.cgi?id=425140">bug</a>
 */
public class InputSemicolonBetweenImports // ok
{
}
