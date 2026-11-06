/*
MatchXpath
query = //METHOD_DEF[./IDENT[@text='test']]


*/

package org.checkstyle.suppressionxpathfilter.coding.matchxpath;

public class InputXpathMatchXpathOne {
    public void test() { } // violation 'Illegal code structure detected.'

    public void correct() { }
}
