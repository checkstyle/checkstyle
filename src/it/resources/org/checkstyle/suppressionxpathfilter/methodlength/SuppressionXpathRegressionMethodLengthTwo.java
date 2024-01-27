package org.checkstyle.suppressionxpathfilter.methodlength;

public class SuppressionXpathRegressionMethodLengthTwo {

    public SuppressionXpathRegressionMethodLengthTwo(){
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
