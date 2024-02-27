/*
IllegalCatch
illegalClassNames = (default)Error, Exception, RuntimeException, Throwable, java.lang.Error, \
                    java.lang.Exception, java.lang.RuntimeException, java.lang.Throwable


*/

package com.puppycrawl.tools.checkstyle.checks.coding.illegalcatch;

public class InputIllegalCatchCheckDefaultTokens {
    public void foo() {
        try { //class names
        } catch (RuntimeException e) { // violation
        } catch (Exception e) { // violation
        } catch (Throwable e) { // violation
        }
    }

    public void bar() {
        try { /* fully qualified class names */
        } catch (java.lang.RuntimeException e) { // violation
        } catch (java.lang.Exception e) { // violation
        } catch (java.lang.Throwable e) { // violation
        }
    }
}
