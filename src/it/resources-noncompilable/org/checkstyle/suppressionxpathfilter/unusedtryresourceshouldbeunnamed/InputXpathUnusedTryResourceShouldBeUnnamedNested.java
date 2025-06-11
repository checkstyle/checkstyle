//non-compiled with javac: Compilable with Java21
package org.checkstyle.suppressionxpathfilter.unusedtryresourceshouldbeunnamed;

public class InputXpathUnusedTryResourceShouldBeUnnamedNested {
    void test() {
        try (AutoCloseable a = lock()) {
            System.out.println(a);

            try (AutoCloseable b = lock()) { // warn

            } catch (Exception e){

            }

        } catch (Exception e) {

        }

        try (AutoClosable _ = lock()) {

        } catch (Exception e) {

        }
    }

    AutoCloseable lock() {
        return null;
    }
}
