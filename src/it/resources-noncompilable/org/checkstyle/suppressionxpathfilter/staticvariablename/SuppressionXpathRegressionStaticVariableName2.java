//non-compiled with javac: Compilable with Java14
package org.checkstyle.suppressionxpathfilter.staticvariablename;

public class SuppressionXpathRegressionStaticVariableName2 {

     public int num1;

     protected int NUM2;

     public void outerMethod() {

         class MyLocalClass {

            static int COUNTERVARIABLE; //warn

        }
    }
 }
