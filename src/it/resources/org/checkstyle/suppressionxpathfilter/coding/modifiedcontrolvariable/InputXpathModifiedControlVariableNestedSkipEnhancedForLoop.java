package org.checkstyle.suppressionxpathfilter.coding.modifiedcontrolvariable;

public class InputXpathModifiedControlVariableNestedSkipEnhancedForLoop {
    void test(){
        String[] strings = {"first","second"};
        for (int i = 0; i < 10; i++) {
            for(String s : strings){
                s += "a"; // ok
            }
            i += 1; // warn
        }
    }
}
