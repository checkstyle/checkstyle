/*
IllegalCatch
illegalClassNames = java.lang.Error, java.lang.Exception, java.lang.Throwable


*/

package com.puppycrawl.tools.checkstyle.checks.coding.illegalcatch;

public class InputIllegalCatch3 {
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
        } catch (java.lang.Exception e) {
        } catch (java.lang.Throwable e) {
        }
    }
}
