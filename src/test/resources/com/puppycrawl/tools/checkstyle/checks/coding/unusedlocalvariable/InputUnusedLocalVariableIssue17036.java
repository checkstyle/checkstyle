/*
UnusedLocalVariable
allowUnnamedVariables = false

*/

package com.puppycrawl.tools.checkstyle.checks.coding.unusedlocalvariable;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;

public class InputUnusedLocalVariableIssue17036 {
    static {
        try (OutputStream os = Files.newOutputStream(null)) {
            System.out.println(os);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            OutputStream os = Files.newOutputStream(null); // violation, unused variable 'os'
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try (OutputStream os = Files.newOutputStream(null)) { // violation, unused variable 'os'
            System.out.println("os");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
