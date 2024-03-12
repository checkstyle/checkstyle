package org.checkstyle.suppressionxpathfilter.methodlength;

public class SuppressionXpathRegressionMethodLengthNoEmptyLines {

    public SuppressionXpathRegressionMethodLengthNoEmptyLines(){
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
