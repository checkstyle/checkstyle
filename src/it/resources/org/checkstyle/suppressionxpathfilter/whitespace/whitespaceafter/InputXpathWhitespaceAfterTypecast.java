/*
WhitespaceAfter
tokens = (default)COMMA, SEMI, TYPECAST, LITERAL_IF, LITERAL_ELSE, LITERAL_WHILE, LITERAL_DO, \
         LITERAL_FOR, LITERAL_FINALLY, LITERAL_RETURN, LITERAL_YIELD, LITERAL_CATCH, \
         DO_WHILE, ELLIPSIS, LITERAL_SWITCH, LITERAL_SYNCHRONIZED, LITERAL_TRY, LITERAL_CASE, \
         LAMBDA, LITERAL_WHEN


*/

package org.checkstyle.suppressionxpathfilter.whitespace.whitespaceafter;

public class InputXpathWhitespaceAfterTypecast {
    Object bad = (Object)null; // violation "'typecast' is not followed by whitespace."
    Object good = (Object) null;
}
