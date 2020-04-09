package com.puppycrawl.tools.checkstyle.checks.coding.redundantthis;

/*
* Config = default
*/
public class InputRedundantThisCatchVariables {
    private Throwable ex;

    public InputRedundantThisCatchVariables(Throwable ex) {
        this.ex = ex; // no violation
    }

    public void run() {
        if (this.ex != null) { // violation
            try {
                debug(this.ex); // violation
            }
            catch (RuntimeException ex) {
                if (ex == this.ex) { // no violation
                    debug(ex);
                }
            }
            catch (Error err) {
                if (err != this.ex) { // violation
                    this.debug(err); // violation
                }
            }
        }
    }

    private void debug(Throwable err) {}
}
