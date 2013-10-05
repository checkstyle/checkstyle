package com.puppycrawl.tools.checkstyle.checks.coding;

public class InputIllegalCatchCheck {
    public void foo() {
        try {
        } catch (RuntimeException e) {
        } catch (Exception e) {
        } catch (Throwable e) {
        }
    }

    public void bar() {
        try {
        } catch (java.lang.RuntimeException e) {
        } catch (java.lang.Exception e) {
        } catch (java.lang.Throwable e) {
        }
    }
}
