// Java17
package org.checkstyle.checks.suppressionxpathfilter.staticvariablename;

public class InputXpathStaticVariableNameInnerClassField {

     public int num1;

     protected int NUM2;

     public void outerMethod() {

         class MyLocalClass {

            static int NUM3; //warn

        }
    }
 }
