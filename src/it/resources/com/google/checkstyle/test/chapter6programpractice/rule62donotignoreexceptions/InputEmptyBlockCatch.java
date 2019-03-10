////////////////////////////////////////////////////////////////////////////////
// Test case file for checkstyle.
// Created: 2001
////////////////////////////////////////////////////////////////////////////////
package com.google.checkstyle.test.chapter6programpractice.rule62donotignoreexceptions;

import java.io.*;
import java.awt.Dimension;
import java.awt.Color;

class InputEmptyBlockCatch {
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
        } catch (Exception e) {}
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
            } catch (Exception e) {}
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
            } catch (Exception e) {}
            finally {} //warn
        }
    };

    void bar1() {
        try {
           if(!flag) {
               doSm();
           }
        } catch (Exception expected) {}
    }

    void bar2() {
        try {
           if(!flag) {
               doSm();
           }
        } catch (Exception expected) {}
    }
}
