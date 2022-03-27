package org.checkstyle.suppressionxpathfilter.methodname;

interface Check {
     int i = 10;
     default void FirstMethod() {} // OK
     default void SecondMethod() {} // OK
     private void ThirdMethod() {} // warn

}

public class SuppressionXpathRegressionMethodName3 implements Check {

     @Override
     public void FirstMethod() {

     }

     @Override
     public void SecondMethod() {

     }
}
