package com.google.checkstyle.test.chapter5naming.rule51identifiernames;

public class InputCatchParameterName {
    {
        try {
        } catch (Exception e) { // ok
        }
        try {
        } catch (Exception ex) { // ok
        }
        try {
        } catch (Error | Exception err) { // ok
        }
        try {
        } catch (Exception exception) { // ok
        }
        try {
        } catch (Exception exception1) { // ok
        }
        try {
        } catch (Exception noWorries) { // ok
        }
        try {
        } catch (Throwable t) { // ok
        }
        try {
            throw new InterruptedException("interruptedException");
        } catch (InterruptedException ie) { // ok
        }
        try {
        } catch (Exception ok) { // ok
            // appropriate to take no action here
        }
        try {
        } catch (Exception e1) { // ok
            try {
            } catch (Exception e2) { // ok
            }
        }
        try {
        } catch (Throwable t1) { // ok
            try {
            } catch (Throwable t2) { // ok
            }
        }
        try {
        } catch (Exception iException) { // warn
        }
        try {
        } catch (Exception ex_1) { // warn
        }
        try {
        } catch (Exception eX) { // warn
        }
        try {
        } catch (Exception eXX) { // warn
        }
        try {
        } catch (Exception x_y_z) { // warn
        }
        try {
        } catch (Exception Ex) { // warn
        }
    }
}
