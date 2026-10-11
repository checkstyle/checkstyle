package org.checkstyle.suppressionxpathfilter.sizes.methodlength;

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
