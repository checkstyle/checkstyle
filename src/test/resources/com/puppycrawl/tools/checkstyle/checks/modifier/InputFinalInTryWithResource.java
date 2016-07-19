package com.puppycrawl.tools.checkstyle.checks.modifier;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

public class InputFinalInTryWithResource {
    public static void test() {
        try {
        }
        catch (RuntimeException e) {
        }

        try (@NotNull BufferedReader br = new BufferedReader(new FileReader(""))) {
        }
        catch (IOException e) {
        }

        try (final BufferedReader br = new BufferedReader(new FileReader(""))) {
        }
        catch (IOException e) {
        }

        try (final BufferedReader br = new BufferedReader(new FileReader(""));
                final BufferedReader br2 = new BufferedReader(new FileReader(""))) {
        }
        catch (IOException e) {
        }
    }
}

@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.LOCAL_VARIABLE})
@interface NotNull {
}
