/*
UnnecessarySemicolonInEnumeration


*/

package com.puppycrawl.tools.checkstyle.checks.coding.unnecessarysemicoloninenumeration;

public class InputUnnecessarySemicolonInEnumeration2 {
    enum DoubleSemicolon {
        A;; // violation 'Unnecessary semicolon'
            // violation above 'Unnecessary semicolon'
    }
    enum DoubleSemicolonNextLine {
        A,B; // violation 'Unnecessary semicolon'
        ; // violation 'Unnecessary semicolon'
    }
    enum TripleSemicolon { // violation below 'Unnecessary semicolon'
        A;;; // violation 'Unnecessary semicolon'
             // violation above 'Unnecessary semicolon'
    }
    enum DoubleSemicolonNoConstants {
        ;; // violation 'Unnecessary semicolon'
           // violation above 'Unnecessary semicolon'
    }
    enum DoubleSemicolonWithComma {
        A,B,;; // violation 'Unnecessary semicolon'
              // violation above 'Unnecessary semicolon'
    }
    enum Semi{
     A,
     b,
     c,
     d,
     e;

     ; // violation 'Unnecessary semicolon'
     void semi(){

     }
    }
}
