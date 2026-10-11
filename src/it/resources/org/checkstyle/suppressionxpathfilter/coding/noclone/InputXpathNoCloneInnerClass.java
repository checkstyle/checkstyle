package org.checkstyle.suppressionxpathfilter.coding.noclone;

public class InputXpathNoCloneInnerClass {

  class InnerClass {
    public Object clone() { return null; } // warn
  }

}
