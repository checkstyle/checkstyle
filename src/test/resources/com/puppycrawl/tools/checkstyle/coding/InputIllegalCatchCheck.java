package com.puppycrawl.tools.checkstyle.coding;

public class InputIllegalCatchCheck {
    public void foo() {
        try { //class names
        } catch (RuntimeException e) {
        } catch (Exception e) {
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
