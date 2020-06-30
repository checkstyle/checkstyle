//non-compiled with javac: Compilable with Java9
package com.puppycrawl.tools.checkstyle.checks.modifier.redundantmodifier;

import java.io.IOException;
import java.io.OutputStreamWriter;

public class InputRedundantModifierTryWithResources {
    public static void main(String[] args) {
        OutputStreamWriter out = null;
        try (out; final OutputStreamWriter out2 = null;) {
            out.write(1);
        } catch (IOException ex) {
            System.err.println(ex);
        }
    }
}
