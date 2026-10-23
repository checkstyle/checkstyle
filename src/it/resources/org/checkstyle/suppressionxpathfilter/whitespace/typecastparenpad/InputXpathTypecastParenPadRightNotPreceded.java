/*
TypecastParenPad
option = space


*/

package org.checkstyle.suppressionxpathfilter.whitespace.typecastparenpad;

public class InputXpathTypecastParenPadRightNotPreceded{
    Object bad = ( Object)null; // violation "')' is not preceded with whitespace."
    Object good = ( Object )null;
}
