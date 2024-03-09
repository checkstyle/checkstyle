package org.checkstyle.suppressionxpathfilter.noclone;

public class InputXpathNoCloneTwo {

  class InnerClass {
    public Object clone() { return null; } // warn
  }

}
