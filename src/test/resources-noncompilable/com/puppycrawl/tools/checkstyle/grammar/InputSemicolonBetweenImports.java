//non-compiled with eclipse: syntax error but works fine in jdk
package com.puppycrawl.tools.checkstyle.grammar;
import java.util.Arrays;
; // non-compilable by eclipse
import java.util.ArrayList;
/**
 * Compilable by javac, but noncompilable by eclipse due to
 * this <a href="https://bugs.eclipse.org/bugs/show_bug.cgi?id=425140">bug</a>
 */
public class InputSemicolonBetweenImports
{
}
