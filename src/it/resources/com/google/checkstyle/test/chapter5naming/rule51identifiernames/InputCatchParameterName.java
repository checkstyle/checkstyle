package com.google.checkstyle.test.chapter5naming.rule51identifiernames;

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
