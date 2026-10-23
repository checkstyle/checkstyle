/*
TypecastParenPad
option = space


*/

package org.checkstyle.suppressionxpathfilter.whitespace.typecastparenpad;

public class InputXpathTypecastParenPadLeftNotFollowed {
    Object bad = (Object )null; // violation "'(' is not followed by whitespace."
    Object good = ( Object )null;
}
