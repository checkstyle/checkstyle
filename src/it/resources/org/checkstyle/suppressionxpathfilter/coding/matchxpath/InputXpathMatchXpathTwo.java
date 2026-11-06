/*
MatchXpath
query = //LITERAL_THROWS[./IDENT[@text='Throwable' or @text='RuntimeException' or \
        ends-with(@text, 'Error')]]


*/

package org.checkstyle.suppressionxpathfilter.coding.matchxpath;

public class InputXpathMatchXpathTwo {
    public void func1() throws RuntimeException {} // violation 'Illegal code structure detected.'
    public void func2() throws Exception {}
    public void func5() throws NullPointerException {}
}
