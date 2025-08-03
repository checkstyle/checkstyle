package org.checkstyle.checks.suppressionxpathfilter.redundantmodifier;

public abstract interface InputXpathRedundantModifierInterface {

    public void m(); //warn
    public int x = 0;

    static enum E {
        A, B, C
  }
}
