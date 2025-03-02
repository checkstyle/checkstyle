/*
NeedBraces
allowSingleLineStatement = (default)false
allowEmptyLoopBody = (default)false
tokens = LITERAL_CASE, LITERAL_DEFAULT


*/

package com.puppycrawl.tools.checkstyle.checks.blocks.needbraces;

public class InputNeedBracesTestCaseDefaultNoSingleLine {

    public String aMethod(int val) {
        switch (val){
        default:
        case 0:
        case -1: break; // violation ''case' construct must use '{}'s'
        case -2: Math.random(); // violation ''case' construct must use '{}'s'
        }
        switch (val){
        default: break; // violation ''default' construct must use '{}'s'
        }
        switch (val){
        default: Math.random(); // violation ''default' construct must use '{}'s'
        }
        switch (val){
        case 1: {}
        default:
        }
        if(false) {
            switch (1) {
                case 1: return "1"; // violation ''case' construct must use '{}'s'
                default: return "2"; // violation ''default' construct must use '{}'s'
                case 0: {return "2";}
                case 2: {break;}
            }
        }
        switch (val) {
        case 0: {
            return "zero";
        }
        case 1: {
            return "one";
        }
        default: {
            return "non-binary";
        }
        }
    }
}
