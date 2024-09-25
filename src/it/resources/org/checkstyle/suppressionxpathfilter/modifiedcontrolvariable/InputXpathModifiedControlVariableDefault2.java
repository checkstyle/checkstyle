package org.checkstyle.suppressionxpathfilter.modifiedcontrolvariable;

public class InputXpathModifiedControlVariableDefault2 {
    public void test() {
        String[] strings = {"first", "second"};
        for(String s : strings){
            s += "a"; // warn
        }
    }
}
