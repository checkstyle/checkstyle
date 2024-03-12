/*
IllegalCatch
illegalClassNames = java.lang.Error, java.lang.Exception, java.lang.Throwable


*/

package com.puppycrawl.tools.checkstyle.checks.coding.illegalcatch;

public class InputIllegalCatchCheckSuperclassThrowable {
    public void foo() {
        try { //class names
        } catch (RuntimeException e) {
        } catch (Exception e) { // violation
        } catch (Throwable e) { // violation
        }
    }

    public void bar() {
        try { /* fully qualified class names */
        } catch (java.lang.RuntimeException e) {
        } catch (java.lang.Exception e) { // violation
        } catch (java.lang.Throwable e) { // violation
        }
    }
}
