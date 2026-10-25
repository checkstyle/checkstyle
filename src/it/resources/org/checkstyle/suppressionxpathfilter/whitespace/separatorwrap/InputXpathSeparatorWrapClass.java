/*
SeparatorWrap
option = (default)eol
tokens = (default)DOT, COMMA


*/

package org.checkstyle.suppressionxpathfilter.whitespace.separatorwrap;

public class InputXpathSeparatorWrapClass {

  String s;

  int a, b;

  void testMethod(int p
                , int q) { // violation "',' should be on the previous line."
  }
}
