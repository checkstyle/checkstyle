/*
TypecastParenPad
option = (default)nospace


*/

package org.checkstyle.suppressionxpathfilter.whitespace.typecastparenpad;

public class InputXpathTypecastParenPadRightPreceded {
    Object bad = (Object )null; // violation "')' is preceded with whitespace."
    Object good = (Object)null;
}
