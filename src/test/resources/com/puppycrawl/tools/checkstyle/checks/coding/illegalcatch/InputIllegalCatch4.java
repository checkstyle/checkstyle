/*
IllegalCatch
illegalClassNames = java.lang.Error, java.lang.Exception, NullPointerException,\
                    java.lang.IOException.


*/

package com.puppycrawl.tools.checkstyle.checks.coding.illegalcatch;

public class InputIllegalCatch4 {
    public void foo() {
        try { //class names
        } catch (RuntimeException e) {
        } catch (Exception e) { // violation
        } catch (Throwable e) {
        }
    }

    public void bar() {
        try { /* fully qualified class names */
        } catch (java.lang.RuntimeException e) {
        } catch (java.lang.Exception e) { // violation
        } catch (java.lang.Throwable e) {
        }
    }
}
