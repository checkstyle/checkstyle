//non-compiled with javac: Compilable with Java16
package org.checkstyle.suppressionxpathfilter.unusedcatchparametershouldbeunnamed;

public class InputXpathPatternVariableAssignmentMethod {

  public void test() {
    if (obj instanceof String s) {
      s = "hello"; // warn
    }
  }

}
