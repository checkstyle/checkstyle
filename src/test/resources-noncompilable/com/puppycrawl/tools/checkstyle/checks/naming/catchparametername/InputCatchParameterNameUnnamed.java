/*
CatchParameterName
format = (default)^(e|t|ex|[a-z][a-z][a-zA-Z]+|_)$


*/

//non-compiled with javac: Compilable with Java21
package com.puppycrawl.tools.checkstyle.checks.naming.catchparametername;

public class InputCatchParameterNameUnnamed {

    void m() {
         try {
        } catch (Exception _) {
        }
        try {
        } catch (Exception __) { // violation, 'Name '__' must match pattern*.'
        }
        try {
        } catch (Error | Exception _) {
        }
        try {
        } catch (Exception _BAD) { // violation, 'Name '_BAD' must match pattern*.'
        }
        try {
        } catch (Exception BAD__) { // violation, 'Name 'BAD__' must match pattern*.'
        }
        try {
        } catch (Throwable _) {
            try {
            } catch (Throwable _) {
            }
        }
    }
}
