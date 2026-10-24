/*
TypecastParenPad
option = (default)nospace


*/

package org.checkstyle.suppressionxpathfilter.whitespace.typecastparenpad;

public class InputXpathTypecastParenPadLeftFollowed {
    Object bad = ( Object)null; // violation "'(' is followed by whitespace."
    Object good = (Object)null;
}
