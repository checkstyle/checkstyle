package org.checkstyle.suppressionxpathfilter.methodname;

interface Check {
     int i = 10;
     default void FirstMethod() {}  
     default void SecondMethod() {}  
     private void ThirdMethod() {} // warn

}

public class InputXpathMethodNameCustomProperties implements Check {

     @Override
     public void FirstMethod() {

     }

     @Override
     public void SecondMethod() {

     }
}
