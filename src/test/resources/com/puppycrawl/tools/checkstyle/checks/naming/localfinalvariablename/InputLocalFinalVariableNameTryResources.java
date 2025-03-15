/*
LocalFinalVariableName
format = [A-Z]+
tokens = (default)VARIABLE_DEF, PARAMETER_DEF, RESOURCE


*/

package com.puppycrawl.tools.checkstyle.checks.naming.localfinalvariablename;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.PropertyResourceBundle;
import java.util.zip.ZipFile;

/**
 * Contains test cases regarding checking local
 * final variable name in try-with-resources statement:
 *
 * @author Valeria Vasylieva
 **/
public class InputLocalFinalVariableNameTryResources {

    void method() throws Exception {
        final String fileName = "Test";
        final BufferedReader br = new BufferedReader(new InputStreamReader( // violation 'Name 'br' must match pattern'
                new FileInputStream(fileName), StandardCharsets.UTF_8));
        try {
        } finally {
            br.close();
        }
    }

    void method2() throws Exception {
        final String fileName = "Test";
        try (BufferedReader br = new BufferedReader(new InputStreamReader( // violation 'Name 'br' must match pattern'
                new FileInputStream(fileName), StandardCharsets.UTF_8))) {
        } finally {

        }
    }

    void method3() throws Exception {
        final String fileName = "Test";
        try (final BufferedReader BR = new BufferedReader(new InputStreamReader(
                new FileInputStream(fileName), StandardCharsets.UTF_8))) {
        } finally {

        }
    }

    void method4() throws Exception {
        final String fileName = "Test";
        try (BufferedReader BR = new BufferedReader(new InputStreamReader(
                new FileInputStream(fileName), StandardCharsets.UTF_8));
             ZipFile zf = new ZipFile(fileName)) { // violation 'Name 'zf' must match pattern'
        } finally {

        }
    }

    void method5() throws Exception {
        final String fileName = "Test";
        try (BufferedReader BR = new BufferedReader(new InputStreamReader(
                new FileInputStream(fileName), StandardCharsets.UTF_8));
             ZipFile ZF = new ZipFile(fileName)) {
        } finally {

        }
    }

    void method6() throws Exception {
        String srcDir = System.getProperty("test.src", ".");
        try (FileInputStream fis8859_1 = new FileInputStream( // violation 'Name 'fis8859_1' must match pattern'
                new File(srcDir, "Bug.properties"));
             FileInputStream fisUTF8 = new FileInputStream(new File(srcDir, "Bug_Utf8.properties"));
             InputStreamReader isrutf8 = new InputStreamReader(fisUTF8, "UTF-8")) { // violation 'Name 'isrutf8' must match pattern'
            PropertyResourceBundle bundleUtf8 = new PropertyResourceBundle(isrutf8);
            PropertyResourceBundle bundle = new PropertyResourceBundle(fis8859_1);
            String[] arrayUtf8 = {"1", "2", "3"};
            String[] array = {"key1", "key2"};
            if (!Arrays.equals(arrayUtf8, array)) {
                throw new RuntimeException("Error message");
            }
        } catch (IOException ioe) {
            throw new RuntimeException(ioe);
        }
    }
}
