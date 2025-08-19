// Java16
package org.checkstyle.suppressionxpathfilter.coding.patternvariableassignment;

public class InputXpathPatternVariableAssignmentMethod {

  public void test(Object obj) {
    if (obj instanceof String s) {
      s = "hello"; // warn
    }
  }

}
