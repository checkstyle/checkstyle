package org.checkstyle.checks.suppressionxpathfilter.methodlength;

public class InputXpathMethodLengthNoEmptyLines {

    public InputXpathMethodLengthNoEmptyLines(){
        // a line
        // a line
        // a line
        // a line
        // a line


    }

    protected void methodOne(){ // warn
        if(true){
            if(true){
            }
        }
    }

}
