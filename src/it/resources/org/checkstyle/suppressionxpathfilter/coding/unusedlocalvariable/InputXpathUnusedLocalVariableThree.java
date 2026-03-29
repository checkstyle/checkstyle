package org.checkstyle.suppressionxpathfilter.coding.unusedlocalvariable;

public class InputXpathUnusedLocalVariableThree {

    public void foo(){
       int i = 0;
       do{
        int c = 15; //warn
        i++;
       }while(i<5);
    }
}
