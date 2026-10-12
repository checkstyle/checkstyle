/*
EmptyLineWrappingInBlock
tokens = CLASS_DEF
topSeparator = (default)empty_line
bottomSeparator = (default)empty_line


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.emptylinewrappinginblock;

public class InputEmptyLineWrappingInBlockSameLineBlankNeighbors {

    int a;

    // violation below ''{' must have exactly one empty line after.'
    class Inner {} // violation ''}' must have exactly one empty line before.'

    int b;

}
