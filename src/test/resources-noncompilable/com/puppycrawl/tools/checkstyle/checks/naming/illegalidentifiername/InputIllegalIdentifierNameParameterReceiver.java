//non-compiled with javac: Compilable with Java14
package com.puppycrawl.tools.checkstyle.checks.naming.illegalidentifiername;

/* Config:
 *
 * default
 */
public record InputIllegalIdentifierNameParameterReceiver() { // ok
    public void foo4(InputIllegalIdentifierNameParameterReceiver this) { // ok
    }

    private class Inner {
        Inner(InputIllegalIdentifierNameParameterReceiver // ok
                  InputIllegalIdentifierNameParameterReceiver.this) { // ok
        }
    }
}
