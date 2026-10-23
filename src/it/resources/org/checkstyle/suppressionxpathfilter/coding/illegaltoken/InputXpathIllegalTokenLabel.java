/*
IllegalToken
tokens = (default)LABELED_STAT


*/

package org.checkstyle.suppressionxpathfilter.coding.illegaltoken;

public class InputXpathIllegalTokenLabel {
  public void myTest() {
    outer: // violation 'Using 'outer:' is not allowed.'
    for (int i = 0; i < 5; i++) {
      if (i == 1) {
        break outer;
      }
    }
  }
}
