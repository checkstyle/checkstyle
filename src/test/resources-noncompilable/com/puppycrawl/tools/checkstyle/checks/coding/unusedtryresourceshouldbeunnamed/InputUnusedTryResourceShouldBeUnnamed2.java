/*
UnusedTryResourceShouldBeUnnamed

*/

//non-compiled with javac: Compilable with Java21
package com.puppycrawl.tools.checkstyle.checks.coding.unusedtryresourceshouldbeunnamed;

import java.io.FileReader;

public class InputUnusedTryResourceShouldBeUnnamed2 {
    void test1() {
        // violation below, 'Unused try resource 'a' should be unnamed'
        try (FileReader a = new FileReader("someFile.txt")) {

        } catch (Exception e) {

        }

        try (FileReader _ = new FileReader("someFile.txt")) {

        } catch (Exception e) {

        }
    }

    void test2() {
        // Currently, we only report the last unused
        // try-resource in the same try block.
        // violation 3 lines below 'Unused try resource 'c' should be unnamed'
        try (FileReader a = new FileReader("someFile.txt");
             FileReader b = new FileReader("someOtherFile.txt");
             FileReader c = new FileReader("anotherFile.txt")
        ) {

        } catch (Exception e){

        }

        try (FileReader _ = new FileReader("someFile.txt");
             FileReader _ = new FileReader("someOtherFile.txt");
             FileReader _ = new FileReader("anotherFile.txt")) {

        } catch (Exception e){

        }
    }

    void test3() {
        try {
            // violation 2 lines below 'Unused try resource 'a' should be unnamed'
            FileReader a = new FileReader("someFile.txt");
            try (a) {

            } catch (Exception e) {
                throw e;
            }

            FileReader b = new FileReader("someFile.txt");
            try (b) {
                System.out.println(b);
            } catch (Exception e) {

            }
        } catch (Exception e){
            System.out.println(e);
        }
    }

    void test4() {
        try {
            // violation 2 lines below 'Unused try resource 'a' should be unnamed'
            final FileReader a = new FileReader("someFile.txt");
            try (a) {

            } catch (Exception e) {

            }

            final FileReader b = new FileReader("someFile.txt");
            try (b) {
                System.out.println(b);
            } catch (Exception e) {

            }
        } catch (Exception e){
            System.out.println(e);
        }
    }

    void  test5() {
        // violation below, 'Unused try resource 'a' should be unnamed'
        try (AutoCloseable a = new AutoCloseable() {
            public void close() {
                System.out.println("closed");
            }
        }) {

        } catch (Exception e) {

        }

        try (AutoCloseable b = new AutoCloseable() {
            public void close() {
                System.out.println("closed");
            }
        }) {
            b.close();
        } catch (Exception e) {

        }
    }

    void test6() {
    }
}
