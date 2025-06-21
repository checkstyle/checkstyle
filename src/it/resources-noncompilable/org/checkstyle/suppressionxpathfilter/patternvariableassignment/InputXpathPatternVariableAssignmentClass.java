//non-compiled with javac: Compilable with Java16
package org.checkstyle.suppressionxpathfilter.patternvariableassignment;

public class InputXpathPatternVariableAssignmentClass {
  public void foo() {
    AnonymousClass annClass = new AnonymousClass() {
      public void test(Object obj) {
        if (obj instanceof Integer x) {
          x = 3; // warn
        }
      }
    };

  }

}
