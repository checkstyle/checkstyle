package org.checkstyle.suppressionxpathfilter.parametername;

class SuppressionXpathRegressionParameterName3{
  void method2(int V2) {} // warn
  @Override
  public boolean equals(Object V3) { // OK
    return true;
  }
}
