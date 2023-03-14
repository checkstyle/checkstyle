/*
CatchParameterName
format = (default)^(e|t|ex|[a-z][a-z][a-zA-Z]+)$


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
        } catch (Exception exception1) { // violation
        }
        try {
        } catch (Exception noWorries) {
        }
        try {
        } catch (Throwable t) {
        }
        try {
            throw new InterruptedException("interruptedException");
        } catch (InterruptedException ie) { // violation
        }
        try {
        } catch (Exception iException) { // violation
        }
        try {
        } catch (Exception ok) { // violation
            // appropriate to take no action here
        }
        try {
        } catch (Exception e1) { // violation
            try {
            } catch (Exception e2) { // violation
            }
        }
        try {
        } catch (Throwable t1) { // violation
            try {
            } catch (Throwable t2) { // violation
            }
        }
    }
}
