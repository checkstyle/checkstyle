/*
IllegalTokenText
format = invalidIdentifier
ignoreCase = (default)false
message = (default)
tokens = IDENT


*/

package org.checkstyle.suppressionxpathfilter.coding.illegaltokentext;

public interface InputXpathIllegalTokenTextInterface {
    // violation below 'Token text matches the illegal pattern 'invalidIdentifier'.'
    void invalidIdentifier();
}
