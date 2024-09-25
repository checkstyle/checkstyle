package org.checkstyle.suppressionxpathfilter.modifiedcontrolvariable;

public class InputXpathModifiedControlVariableNestedWithForeach {
    void test(){
        String[] strings = {"first","second"};
        for (int i = 0; i < 10; i++) {
            for(String s : strings){
                s += "a"; // warn
            }
        }
    }
}
