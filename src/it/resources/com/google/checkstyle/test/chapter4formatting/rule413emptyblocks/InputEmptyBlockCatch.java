////////////////////////////////////////////////////////////////////////////////
// Test case file for checkstyle.
// Created: 2001
////////////////////////////////////////////////////////////////////////////////
package com.google.checkstyle.test.chapter4formatting.rule413emptyblocks;

import java.io.*; 
import java.awt.Dimension;
import java.awt.Color;

class Catch {
    boolean flag;
    void doSm() {}
    void foo() {
        try {
           if (!flag) {
               doSm();
           }
        } catch (Exception e) { /* ignore */ } //ok
        finally {/* ignore */} //ok
    }

    void foo2() {
        try {
           if (!flag) {
               doSm();
           }
        } catch (Exception e) {} //warn
        finally {} //warn
    }

    class Inner {
        boolean flag;
        void doSm() {}
        void foo() {
            try {
               if (!flag) {
                   doSm();
               }
            } catch (Exception e) { /* ignore */ } //ok
            finally {/* ignore */} //ok
        }

        void foo2() {
            try {
               if (!flag) {
                   doSm();
               }
            } catch (Exception e) {} //warn
            finally {} //warn
        }
    }
    
    Inner anon = new Inner(){
        boolean flag;
        void doSm() {}
        void foo() {
            try {
               if (!flag) {
                   doSm();
               }
            } catch (Exception e) { /* ignore */ } //ok
            finally {/* ignore */} //ok
        }

        void foo2() {
            try {
               if (!flag) {
                   doSm();
               }
            } catch (Exception e) {} //warn
            finally {} //warn
        }
    };
}
