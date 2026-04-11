/*
CatchParameterName
format = (default)^(e|t|ex|[a-z][a-z][a-zA-Z]+|_)$


*/

package com.puppycrawl.tools.checkstyle.checks.naming.catchparametername;

public class InputCatchParameterName {
    {
        try {
        } catch (Exception e) {
        }
        try {
        } catch (Exception ex) {
        }
        try {
        } catch (Error | Exception err) {
        }
        try {
        } catch (Exception exception) {
        }
        try {
        } catch (Exception exception1) { // violation, 'Name 'exception1' must match pattern'
        }
        try {
        } catch (Exception noWorries) {
        }
        try {
        } catch (Throwable t) {
        }
        try {
            throw new InterruptedException("interruptedException");
        } catch (InterruptedException ie) { // violation, 'Name 'ie' must match pattern'
        }
        try {
        } catch (Exception iException) { // violation, 'Name 'iException' must match pattern'
        }
        try {
        } catch (Exception ok) { // violation, 'Name 'ok' must match pattern'
            // appropriate to take no action here
        }
        try {
        } catch (Exception e1) { // violation, 'Name 'e1' must match pattern'
            try {
            } catch (Exception e2) { // violation, 'Name 'e2' must match pattern'
            }
        }
        try {
        } catch (Throwable t1) { // violation, 'Name 't1' must match pattern'
            try {
            } catch (Throwable t2) { // violation, 'Name 't2' must match pattern'
            }
        }
    }
}
