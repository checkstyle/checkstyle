package org.checkstyle.suppressionxpathfilter.coding.illegalcatch;

public class InputXpathIllegalCatchThree {
    public void methodOne(){
        try {
        } catch (Throwable e ) { // warn
        }
    }
}
