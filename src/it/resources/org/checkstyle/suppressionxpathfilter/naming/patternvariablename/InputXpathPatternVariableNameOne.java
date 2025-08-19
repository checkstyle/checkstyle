// java21
package org.checkstyle.suppressionxpathfilter.naming.patternvariablename;

public class InputXpathPatternVariableNameOne {
   void MyClass(Object o1){
       if (o1 instanceof String STRING1) { // warning
       }
   }
}
