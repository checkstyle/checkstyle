/*
UnusedCatchParameterShouldBeUnnamed

*/

//non-compiled with javac: Compilable with Java21
package com.puppycrawl.tools.checkstyle.checks.coding.unusedcatchparametershouldbeunnamed;

import java.io.File;

public class InputUnusedCatchParameterShouldBeUnnamedWithResourceAndFinally {
    private E e;
    Exception exception;

    void testMultiCatch() {
        try {
            int y = 5;
            File file = new File("file.txt");
        } catch (NullPointerException | IllegalArgumentException e) { // violation
            this.e.printStackTrace();
        }

        try {
            int y = 5;
            File file = new File("file.txt");
        } catch (NullPointerException | IllegalArgumentException _) {
            this.e.printStackTrace();
        }

        try {
            File file = new File("file.txt");
        } catch (NullPointerException | IllegalArgumentException e) {
            e.printStackTrace();
        }

        try {
            File file = new File("file.txt");
        } catch (NullPointerException e) { // violation
            System.out.println("null pointer exception");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void testTryWithResource() {
        try (var a = lock()) {
            int y = 5;
        } catch (Exception e) {  // violation
            // no code
        }

        try (var a = lock()) {
            int y = 5;
        } catch (Exception e) {
            System.out.println(e.toString());
        }

        try (var a = lock()) {
            int y = 5;
        } catch (Exception _) {
            System.out.println(e.toString());
        }

    }

    void tryCatchFinally() {
        try {
            File file = new File("file.txt");
        } catch (NullPointerException | IllegalArgumentException e) { // violation
            this.e.printStackTrace();
        } finally {
            System.out.println(e); // catch parameter is not in this scope
        }

        try {
            File file = new File("file.txt");
        } catch (NullPointerException | IllegalArgumentException e) {
            e.printStackTrace();
        } finally {
            System.out.println("close the file");
        }
    }

    void e() {
    }

    AutoCloseable lock() {
        return null;
    }
}

class E {
    void printStackTrace() {
    }
}

class A {
}
