package org.checkstyle.suppressionxpathfilter.separatorwrap;

import java.util.Arrays;

public class InputXpathSeparatorWrapMethod {
  void method1() {
    String[] stringArray = {"foo", "bar"};
    int stringLength = stringArray
            .length; // warn
  }

  void method2() {
    String[] stringArray = {"foo", "bar"};
    int stringLength = stringArray.length;
  }
}
