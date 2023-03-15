/*
EmptyForIteratorPad
option = (default)nospace


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.emptyforiteratorpad;

public class InputEmptyForIteratorPadWithEmoji {

    void method1() {
        String s = "asdda🤩🎄🧐";
        int j = 0;
        for (int i = 0; i < s.length() && s.substring(i) == "🤩🎄🧐"; i++) {

        }

        for (int i = 0; i < s.length() && s.substring(i) == "🤩🎄🧐asd";i++) {

        }

        for(;j < s.length() &&
                s.substring(j) =="🤩🎄"; ) { // violation '';' is followed by whitespace.'

        }

        s = "🤩a🤩"; for (;  j <s.length()
                && s.substring(j) =="🤩🧐";
      j++ ) {

        }

        s = "da🤩da🤩"; for (  ;
                                j < s.length() && s.substring(j) == "🤩🎄🧐"
                ;
  ) {

        }
    }
}
