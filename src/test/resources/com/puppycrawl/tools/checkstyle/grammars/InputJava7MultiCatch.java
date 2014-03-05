package com.puppycrawl.tools.checkstyle.grammars;

import java.io.*;

/**
 * Input for Java 7 multi-catch.
 */
public class InputJava7MultiCatch
{
    public static class CustomException extends Exception { }
    public static class AnotherCustomException extends Exception { }

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
        } catch (final FileNotFoundException | CustomException | test.foo.AnotherCustomException e) {
            logException(e);
        }
    }
}
