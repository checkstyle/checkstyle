package com.puppycrawl.tools.checkstyle.checks.coding.requirethis;

public class InputRequireThisCatchVariables extends Thread {
    private Throwable ex;

    public InputRequireThisCatchVariables(Throwable ex) {
        this.ex = ex;
    }

    @Override
    public void run() {
        if (this.ex != null) {
            try {
                exceptional(this.ex);
            }
            catch (RuntimeException ex) {
                if (ex == this.ex) {
                    debug("Expected exception thrown", ex);
                }
                else {
                    ex.printStackTrace();
                }
            }
            catch (Error err) {
                if (err == this.ex) {
                    debug("Expected exception thrown", err);
                }
                else {
                    ex.printStackTrace();
                }
            }
            catch (Throwable ex) {
                ex.printStackTrace();
            }
        }
    }

    private static void exceptional(Throwable ex) {}
    private static void debug(String message, Throwable err) {}
}
