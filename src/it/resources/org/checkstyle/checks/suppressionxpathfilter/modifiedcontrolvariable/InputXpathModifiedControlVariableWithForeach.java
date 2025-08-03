package org.checkstyle.checks.suppressionxpathfilter.modifiedcontrolvariable;

public class InputXpathModifiedControlVariableWithForeach {
    public void test() {
        String[] strings = {"first", "second"};
        for(String s : strings){
            s += "a"; // warn
        }
    }
}
