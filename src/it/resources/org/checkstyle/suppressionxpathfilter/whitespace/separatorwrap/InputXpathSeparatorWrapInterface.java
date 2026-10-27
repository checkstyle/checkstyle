/*
SeparatorWrap
option = (default)eol
tokens = ELLIPSIS


*/

package org.checkstyle.suppressionxpathfilter.whitespace.separatorwrap;

interface InputXpathSeparatorWrapInterface {

    public void testMethod1(String...
            parameters);

    public void testMethod2(String
            ...parameters); // violation "'...' should be on the previous line."
}
