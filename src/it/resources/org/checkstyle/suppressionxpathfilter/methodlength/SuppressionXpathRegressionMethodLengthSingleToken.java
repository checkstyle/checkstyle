package org.checkstyle.suppressionxpathfilter.methodlength;

public class SuppressionXpathRegressionMethodLengthSingleToken {

    public void methodOne() { }

    SuppressionXpathRegressionMethodLengthSingleToken a =
            new SuppressionXpathRegressionMethodLengthSingleToken(){
        public void methodOne(){ // warn

        }
    };
}
