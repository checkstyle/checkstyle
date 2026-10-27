/*
SeparatorWrap
option = (default)eol
tokens = DOT


*/

package org.checkstyle.suppressionxpathfilter.whitespace.separatorwrap;

import java.util.Arrays;

public class InputXpathSeparatorWrapMethod {
  void method1() {
    String[] stringArray = {"foo", "bar"};
    int stringLength = stringArray
            .length; // violation "'.' should be on the previous line."
  }

  void method2() {
    String[] stringArray = {"foo", "bar"};
    int stringLength = stringArray.length;
  }
}
