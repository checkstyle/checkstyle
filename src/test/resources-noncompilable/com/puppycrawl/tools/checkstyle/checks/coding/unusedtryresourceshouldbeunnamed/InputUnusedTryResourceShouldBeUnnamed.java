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

        try (AutoCloseable b = lock()) {
            AutoCloseable c = b;
        } catch (Exception e) {

        }

        try (AutoCloseable d = lock()) {
            d.close();
        } catch (Exception e){
            System.out.println(e.getMessage());
        }

        // violation below, 'Unused try resource 'e' should be unnamed'
        try (AutoCloseable e = lock()) {

        } catch (Exception e){
            System.out.println(e.getMessage());
        }

        try (AutoCloseable _ = lock()) {

        } catch (Exception e) {

        }

        try {

        } catch (Exception e) {

        }

        // violation 2 lines below 'Unused try resource 'b' should be unnamed'
        try (AutoCloseable a =  lock();
             AutoCloseable b = lock()) {
            System.out.println(a);
        } catch (Exception e){

        }

        try (AutoCloseable a = lock();
             AutoCloseable _ = lock()) {
            System.out.println(a);
        } catch (Exception e){

        }
    }

    AutoCloseable lock() {
        return null;
    }
}
