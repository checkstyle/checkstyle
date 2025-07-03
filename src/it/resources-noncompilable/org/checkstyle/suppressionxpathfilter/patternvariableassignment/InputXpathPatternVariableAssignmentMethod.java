// non-compiled with javac: Compilable with Java16
package org.checkstyle.suppressionxpathfilter.patternvariableassignment;

public class InputXpathPatternVariableAssignmentMethod {

  public void test(Object obj) {
    if (obj instanceof String s) {
      s = "hello"; // warn
    }
  }

}
