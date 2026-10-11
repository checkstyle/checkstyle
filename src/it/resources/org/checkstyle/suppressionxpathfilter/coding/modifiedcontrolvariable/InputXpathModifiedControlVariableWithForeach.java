package org.checkstyle.suppressionxpathfilter.coding.modifiedcontrolvariable;

public class InputXpathModifiedControlVariableWithForeach {
    public void test() {
        String[] strings = {"first", "second"};
        for(String s : strings){
            s += "a"; // warn
        }
    }
}
