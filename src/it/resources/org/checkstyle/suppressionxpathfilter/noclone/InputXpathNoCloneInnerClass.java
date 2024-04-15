package org.checkstyle.suppressionxpathfilter.noclone;

public class InputXpathNoCloneInnerClass {

  class InnerClass {
    public Object clone() { return null; } // warn
  }

}
