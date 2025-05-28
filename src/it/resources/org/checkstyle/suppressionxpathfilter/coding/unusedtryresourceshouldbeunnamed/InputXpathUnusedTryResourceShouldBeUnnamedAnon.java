package org.checkstyle.suppressionxpathfilter.coding.unusedtryresourceshouldbeunnamed;

class InputXpathUnusedTryResourceShouldBeUnnamedAnon {

    void test() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try (AutoCloseable a = lock()) { // warn

                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        };
    }
}
