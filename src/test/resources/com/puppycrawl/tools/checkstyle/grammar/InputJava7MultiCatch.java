/*
com.puppycrawl.tools.checkstyle.checks.naming.MemberNameCheck
format = (default)^[a-z][a-zA-Z0-9]*$
applyToPublic = (default)true
applyToProtected = (default)true
applyToPackage = (default)true
applyToPrivate = (default)true


*/

package com.puppycrawl.tools.checkstyle.grammar;

import java.io.*;

/**
 * Input for Java 7 multi-catch.
 */
public class InputJava7MultiCatch // ok
{
    public static class CustomException extends Exception { }
    public static class AnotherCustomException extends RuntimeException { }

    public static void logException(Exception e) { }

    public static void main(String[] args) {
        try {
            FileInputStream in = new FileInputStream("InputJava7MultiCatch.java");
            throw new CustomException();
        } catch (FileNotFoundException | CustomException e) {
            logException(e);
        }

        try {
            FileInputStream in = new FileInputStream("InputJava7MultiCatch.java");
            throw new CustomException();
        } catch (final FileNotFoundException | CustomException |
            com.puppycrawl.tools.checkstyle.grammar.InputJava7MultiCatch.AnotherCustomException e) {
            logException(e);
        }
    }
}
