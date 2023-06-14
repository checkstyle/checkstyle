/*
NoWhitespaceBeforeCaseDefaultColon


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.nowhitespacebeforecasedefaultcolon;

import java.time.DayOfWeek;

class Example1 {
  void example() {
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
      case 3
              : break; // violation '':' is preceded with whitespace'
      case
          5: break;
      default
          : // violation '':' is preceded with whitespace'
          break;
    }

    switch(DayOfWeek.MONDAY) {
      case MONDAY: System.out.println("  6"); break;
      // violation below '':' is preceded with whitespace'
      case TUESDAY               : System.out.println("  7"); break;
    }
  }
}
