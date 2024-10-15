package org.checkstyle.suppressionxpathfilter.localvariablename;

public class InputXpathRegressionLocalVariableNameIteration {

  void MyMethod() {
    for (int var = 1; var < 10; var++) {}
    for (int var_1 = 0; var_1 < 10; var_1++) {} // warn
  }

}
