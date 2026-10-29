/*
PatternVariableName
format = (default)^([a-z][a-zA-Z0-9]*|_)$


*/

package org.checkstyle.suppressionxpathfilter.naming.patternvariablename;

public class InputXpathPatternVariableNameOne {
   void MyClass(Object o1){
       // violation below "Name 'STRING1' must match pattern '^([a-z][a-zA-Z0-9]*|_)$'."
       if (o1 instanceof String STRING1) {
       }
   }
}
