package com.google.checkstyle.test.chapter5naming.rule526parameternames;

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
        } catch (Exception exception1) {
        }
        try {
        } catch (Exception noWorries) {
        }
        try {
        } catch (Throwable t) {
        }
        try {
            throw new InterruptedException("interruptedException");
        } catch (InterruptedException ie) {
        }
        try {
        } catch (Exception ok) {
            // appropriate to take no action here
        }
        try {
        } catch (Exception e1) {
            try {
            } catch (Exception e2) {
            }
        }
        try {
        } catch (Throwable t1) {
            try {
            } catch (Throwable t2) {
            }
        }
        try {
        } catch (Exception iException) {
        // violation above 'Catch parameter name 'iException' must match pattern'
        }
        try {
        } catch (Exception ex_1) { // violation 'Catch parameter name 'ex_1' must match pattern'
        }
        try {
        } catch (Exception eX) { // violation 'Catch parameter name 'eX' must match pattern'
        }
        try {
        } catch (Exception eXX) { // violation 'Catch parameter name 'eXX' must match pattern'
        }
        try {
        } catch (Exception x_y_z) { // violation 'Catch parameter name 'x_y_z' must match pattern'
        }
        try {
        } catch (Exception Ex) { // violation 'Catch parameter name 'Ex' must match pattern'
        }
    }
}
