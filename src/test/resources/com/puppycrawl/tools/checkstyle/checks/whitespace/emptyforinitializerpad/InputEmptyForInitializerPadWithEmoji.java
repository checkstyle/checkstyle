/*
EmptyForInitializerPad
option = SPACE


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.emptyforinitializerpad;

public class InputEmptyForInitializerPadWithEmoji {

    void method1() {
        String s = "asdda🤩🎄🧐";
        int j = 0;
        for (int i = 0; i < s.length() && s.substring(i) == "🤩🎄🧐" ; ) {

        }

        for (int i = 0; i < s.length() && s.substring(i) == "🤩🎄🧐asd";i++) {

        }

        for(;j < s.length() && // violation '';' is not preceded with whitespace'
                s.substring(j) =="🤩🎄";) {

        }

        s = "🤩a🤩"; for (;j <s.length() // violation '';' is not preceded with whitespace.'
                && s.substring(j) =="🤩🧐";
              j++ ) {

        }

        s = "da🤩da🤩"; for (  ;
              j < s.length() && s.substring(j) == "🤩🎄🧐"
                ;
              j++ ) {

        }
    }
}
