/*
OperatorWrap
option = eol
tokens = ASSIGN


*/

package org.checkstyle.suppressionxpathfilter.whitespace.operatorwrap;

public class InputXpathOperatorWrapPreviousLine {
    int b
          = 10; // violation ''=' should be on the previous line.'
    int c =
             10;
}
