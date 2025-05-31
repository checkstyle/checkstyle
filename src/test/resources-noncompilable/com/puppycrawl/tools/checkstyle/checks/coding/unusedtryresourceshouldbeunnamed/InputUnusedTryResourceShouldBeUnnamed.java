/*
UnusedTryResourceShouldBeUnnamed

*/

//non-compiled with javac: Compilable with Java21
package com.puppycrawl.tools.checkstyle.checks.coding.unusedtryresourceshouldbeunnamed;

public class InputUnusedTryResourceShouldBeUnnamed {
    void test() {
        // violation below, 'Unused try resource 'a' should be unnamed'
        try (AutoCloseable a = lock()) {

        } catch (Exception e) {

        }

        // violation below, 'Unused try resource 'b' should be unnamed'
        try (AutoCloseable b = lock()) {
            b = lock();
        } catch (Exception e) {

        }

        try (AutoCloseable c = lock()) {
            AutoCloseable d = c;
        } catch (Exception e) {

        }

        // violation below, 'Unused try resource 'e' should be unnamed'
        try (AutoClosable e = lock()) {

        } catch (Exception e){
            System.out.println(e.getMessage());
        }

        try (AutoClosable f = lock()) {
            f.close();
        } catch (Exception e){
            System.out.println(e.getMessage());
        }

        // violation below, 'Unused try resource 'g' should be unnamed'
        try (AutoClosable g = lock()) {
            g = new FileReader("someFile.txt");
        } catch (Exception e){
            System.out.println(e.getMessage());
        }

        try (AutoClosable _ = lock()) {

        } catch (Exception e) {

        }

        try {

        } catch (Exception e) {

        }
    }

    AutoCloseable lock() {
        return null;
    }
}
