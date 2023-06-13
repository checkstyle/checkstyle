/*
NoWhitespaceBeforeCaseDefaultColon


*/
//non-compiled with javac: Compilable with Java14
package com.puppycrawl.tools.checkstyle.checks.whitespace.nowhitespacebeforecasedefaultcolon;

import java.time.DayOfWeek;

class Example1 {
  void example() {
    // xdoc section -- start
    switch(1) {
      case 1 : // violation '':' is preceded with whitespace'
        break;
      case 2:
        break;
      default : // violation '':' is preceded with whitespace'
        break;
    }

    switch(2) {
      case 2:
        break;
      case 3, 4
        : break; // violation '':' is preceded with whitespace'
      case 5,
        6: break;
      default
            : // violation '':' is preceded with whitespace'
        break;
    }

    switch(DayOfWeek.MONDAY) {
      case MONDAY, FRIDAY, SUNDAY: System.out.println("  6"); break;
      // violation below '':' is preceded with whitespace'
      case TUESDAY               : System.out.println("  7"); break;
    }
    // xdoc section -- end
  }
}
